package mrriegel.cwacom.tile;

import java.util.Random;

import mrriegel.cwacom.CWACOM;
import mrriegel.cwacom.util.RWLUtils;
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
	private EnergyStorage en = new EnergyStorage(200000, 1000, 0);
	private TileFldsmdfr tf;
	private int tfX, tfY, tfZ;

	public TileTerminal() {
		super();
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		tfX = tag.getInteger("tfX");
		tfY = tag.getInteger("tfY");
		tfZ = tag.getInteger("tfZ");
		en.readFromNBT(tag);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("tfX", tfX);
		tag.setInteger("tfY", tfY);
		tag.setInteger("tfZ", tfZ);
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
		if (worldObj.isRemote
				|| worldObj.getBlockPowerInput(xCoord, yCoord, zCoord) > 0
				|| tf == null
				|| tf.getTankInfo(ForgeDirection.DOWN)[0].fluid == null)
			return;
		for (Object o : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
			EntityPlayer player = (EntityPlayer) o;
			Chunk c = worldObj.getChunkFromBlockCoords(
					RWLUtils.double2int(player.posX),
					RWLUtils.double2int(player.posZ));

			for (int i = c.xPosition * 16; i < c.xPosition * 16 + 16; i++) {
				for (int j = c.zPosition * 16; j < c.zPosition * 16 + 16; j++) {
					Random rand = new Random();
					if (rand.nextInt(6000) == 0) {
						EntityItem ei = new EntityItem(worldObj, i
								+ rand.nextDouble() - 0.5D, 300, j
								+ rand.nextDouble() - 0.5D, CWACOM.foodList
								.get(new Random().nextInt(CWACOM.foodList
										.size())).copy());
						int health = ((ItemFood) ei.getEntityItem().getItem())
								.func_150905_g(ei.getEntityItem().copy());
						if (health * 5000 <= getEnergyStored(ForgeDirection.DOWN)
								&& tf.getTankInfo(ForgeDirection.DOWN)[0].fluid.amount >= health * 500
								&& tf.yCoord >= 200
								&& (worldObj.provider.dimensionId != -1 && worldObj.provider.dimensionId != 1)) {
							worldObj.spawnEntityInWorld(ei);
							ei.setVelocity(0D, -10D, 0D);

							en.modifyEnergyStored(-health * 5000);
							tf.getTankInfo(ForgeDirection.DOWN)[0].fluid.amount = tf
									.getTankInfo(ForgeDirection.DOWN)[0].fluid.amount
									- health * 100;
							worldObj.markBlockForUpdate(tfX, tfY, tfZ);
							worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
						}
					}
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
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
