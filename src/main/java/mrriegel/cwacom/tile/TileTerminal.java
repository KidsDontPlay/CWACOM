package mrriegel.cwacom.tile;

import java.util.Random;

import mrriegel.cwacom.CWACOM;
import mrriegel.cwacom.config.ConfigurationHandler;
import mrriegel.cwacom.util.RiegelUtils;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;

public class TileTerminal extends TileEntity implements IEnergyReceiver {
	private EnergyStorage en = new EnergyStorage(200000, 2000, 0);
	private TileFldsmdfr tf;
	private int tfX, tfY, tfZ, count, rate;
	long cooldown;

	public TileTerminal() {
		super();
		rate = 5;
		count = 1;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		tfX = tag.getInteger("tfX");
		tfY = tag.getInteger("tfY");
		tfZ = tag.getInteger("tfZ");
		count = tag.getInteger("count");
		rate = tag.getInteger("rate");
		cooldown = tag.getLong("cooldown");
		en.readFromNBT(tag);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("tfX", tfX);
		tag.setInteger("tfY", tfY);
		tag.setInteger("tfZ", tfZ);
		tag.setInteger("count", count);
		tag.setInteger("rate", rate);
		tag.setLong("cooldown", cooldown);
		en.writeToNBT(tag);
	}

	@Override
	public void updateEntity() {
		if (tf == null || tf.xCoord != tfX || tf.yCoord != tfY
				|| tf.zCoord != tfZ)
			if (worldObj.getTileEntity(tfX, tfY, tfZ) instanceof TileFldsmdfr)
				tf = (TileFldsmdfr) worldObj.getTileEntity(tfX, tfY, tfZ);
		if (worldObj.getTileEntity(tfX, tfY, tfZ) == null
				|| (worldObj.getTileEntity(tfX, tfY, tfZ) != null)
				&& !(worldObj.getTileEntity(tfX, tfY, tfZ) instanceof TileFldsmdfr)) {
			tfX = xCoord;
			tfY = yCoord;
			tfZ = zCoord;
			tf = null;
		}
		if (worldObj.isRemote)
			return;
		if (worldObj.getBlockPowerInput(xCoord, yCoord, zCoord) > 0
				|| tf == null
				|| tf.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid == null) {
			if (tf != null) {
				tf.setActive(false);
				worldObj.markBlockForUpdate(tfX, tfY, tfZ);
			}
			return;
		}
		if (rate == 0) {
			if (tf != null)
				tf.setActive(false);
			return;
		}
		tf.setActive(true);
		cooldown = worldObj.getTotalWorldTime()
				% ((long) Math.pow(2, (10 - rate)) * 20);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		for (Object o : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
			EntityPlayer player = (EntityPlayer) o;
			Chunk c = worldObj.getChunkFromBlockCoords(
					RiegelUtils.double2int(player.posX),
					RiegelUtils.double2int(player.posZ));

			Random rand = new Random();
			if (cooldown == 0) {
				EntityItem ei = new EntityItem(
						worldObj,
						c.xPosition * 16 + (rand.nextInt(16) + 1)
								+ rand.nextDouble() - 0.5D,
						300,
						c.zPosition * 16 + (rand.nextInt(16) + 1)
								+ rand.nextDouble() - 0.5D,
						CWACOM.foodList
								.get(count == 0 ? new Random()
										.nextInt(CWACOM.foodList.size() - 1) + 1
										: count).copy());
				int health = ((ItemFood) ei.getEntityItem().getItem())
						.func_150905_g(ei.getEntityItem().copy());
				if (health * ConfigurationHandler.rfCost <= getEnergyStored(ForgeDirection.UNKNOWN)
						&& tf.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid.amount >= health
								* ConfigurationHandler.waterCost
						&& tf.yCoord >= 200
						&& (worldObj.provider.dimensionId != -1 && worldObj.provider.dimensionId != 1)
						&& worldObj.getChunkFromBlockCoords(tf.xCoord,
								tf.zCoord).isChunkLoaded
						&& player.worldObj.provider.dimensionId == worldObj.provider.dimensionId) {
					worldObj.spawnEntityInWorld(ei);
					// ei.setVelocity(0D, -10D, 0D);
					ei.motionX = 0D;
					ei.motionY = -10D;
					ei.motionZ = 0D;

					en.modifyEnergyStored(-health * ConfigurationHandler.rfCost);
					tf.drain(ForgeDirection.UNKNOWN, health
							* ConfigurationHandler.waterCost, true);
					worldObj.markBlockForUpdate(tfX, tfY, tfZ);
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					markDirty();
				}
			}
		}
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound syncData = new NBTTagCompound();
		this.writeToNBT(syncData);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord,
				this.zCoord, 1, syncData);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.func_148857_g());
	}

	public EnergyStorage getEn() {
		return en;
	}

	public void setEn(EnergyStorage en) {
		this.en = en;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	public void setTfX(int tfX) {
		this.tfX = tfX;
	}

	public void setTfY(int tfY) {
		this.tfY = tfY;
	}

	public void setTfZ(int tfZ) {
		this.tfZ = tfZ;
	}

	public TileFldsmdfr getTf() {
		return tf;
	}

	public void setTf(TileFldsmdfr tf) {
		this.tf = tf;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public long getCooldown() {
		return cooldown;
	}

	public void setCooldown(long cooldown) {
		this.cooldown = cooldown;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive,
			boolean simulate) {
		int result = Math.min(maxReceive, en.getMaxReceive());

		if ((en.getEnergyStored() + result) > en.getMaxEnergyStored()) {
			result = en.getMaxEnergyStored() - en.getEnergyStored();
		} else
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

		if (!simulate) {
			en.setEnergyStored(en.getEnergyStored() + result);
		}

		return result;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return en.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return en.getMaxEnergyStored();
	}
}
