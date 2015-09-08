package mrriegel.cwacom.tile;

import java.util.Random;
import java.util.Vector;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import mrriegel.cwacom.CWACOM;
import mrriegel.cwacom.init.ModItems;
import mrriegel.cwacom.util.BlockLocation;
import mrriegel.cwacom.util.RWLUtils;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.ForgeDirection;

public class TileTerminal extends TileEntity implements IEnergyReceiver {
	private Integer x, y, z;
	private EnergyStorage en = new EnergyStorage(100000, 1000, 0);
	private ItemStack stack;

	public TileTerminal() {
		super();
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		x = tag.getInteger("x");
		y = tag.getInteger("y");
		z = tag.getInteger("z");
		en.readFromNBT(tag);

		NBTTagCompound st = (NBTTagCompound) tag.getTag("stack");
		stack = ItemStack.loadItemStackFromNBT(st);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		if (x != null)
			tag.setInteger("x", x);
		if (y != null)
			tag.setInteger("y", y);
		if (z != null)
			tag.setInteger("z", z);
		en.writeToNBT(tag);

		NBTTagCompound st = new NBTTagCompound();
		if (stack != null)
			stack.writeToNBT(st);

		tag.setTag("stack", st);
	}

	@Override
	public void updateEntity() {
		if (worldObj.isRemote)
			return;
		// EntityItem ei = new EntityItem(worldObj, xCoord + 0.5D, yCoord +
		// 0.5D,
		// zCoord + 0.5D, new ItemStack(Items.cooked_porkchop));
		// worldObj.spawnEntityInWorld(ei);
		// ei.setPosition(ei.posX, ei.posY, ei.posZ);
		// System.out.println("num: " + ei.getEntityItem().stackSize);
		Vector<EntityPlayer> lis = new Vector<EntityPlayer>();
		for (World w : MinecraftServer.getServer().worldServers)
			for (Object o : w.playerEntities) {
				EntityPlayer p = (EntityPlayer) o;
				lis.add(p);
			}
		for (EntityPlayer player : lis) {
			// Chunk c = worldObj.getChunkFromBlockCoords(
			// RWLUtils.double2int(player.posX),
			// RWLUtils.double2int(player.posZ));

			// for (int i = c.xPosition * 16; i < c.xPosition * 16 + 16; i++) {
			// for (int j = c.zPosition * 16; j < c.zPosition * 16 + 16; j++) {
			if (new Random().nextInt(5) == 2) {
				EntityItem ei = new EntityItem(worldObj, player.posX,
						player.posY, player.posZ, /*new ItemStack(Items.golden_apple)*/CWACOM.foodList.get(5));
				worldObj.spawnEntityInWorld(ei);
				System.out.println("num: " + ei.getEntityItem().stackSize);
				// }
				// }
			}
		}
	}

//	@Override
//	public Packet getDescriptionPacket() {
//		NBTTagCompound syncData = new NBTTagCompound();
//		this.writeToNBT(syncData);
//		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord,
//				this.zCoord, 1, syncData);
//	}
//
//	@Override
//	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
//		readFromNBT(pkt.func_148857_g());
//		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
//		System.out.println("onData");
//	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
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

	public ItemStack getStack() {
		return stack;
	}

	public void setStack(ItemStack stack) {
		this.stack = stack;
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
