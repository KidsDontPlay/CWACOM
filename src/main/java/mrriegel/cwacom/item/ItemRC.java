package mrriegel.cwacom.item;

import java.util.List;

import mrriegel.cwacom.CWACOM;
import mrriegel.cwacom.CreativeTab;
import mrriegel.cwacom.init.ModBlocks;
import mrriegel.cwacom.reference.Reference;
import mrriegel.cwacom.tile.TileTerminal;
import mrriegel.cwacom.util.NBTHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemRC extends Item {
	public ItemRC() {
		super();
		this.setCreativeTab(CreativeTab.tab1);
		this.setUnlocalizedName(Reference.MOD_ID + ":" + "rc");
		this.setTextureName(Reference.MOD_ID + ":" + "rc");
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world,
			int x, int y, int z, int side, float p_77648_8_, float p_77648_9_,
			float p_77648_10_) {
		if (!world.isRemote) {
			EntityItem ei = new EntityItem(world, player.posX, player.posY,
					player.posZ, new ItemStack(Items.cooked_porkchop));
			world.spawnEntityInWorld(ei);
			System.out.println("num: " + ei.getEntityItem().stackSize);
			return false;
		}

		if (player.isSneaking()
				&& world.getBlock(x, y, z).equals(ModBlocks.fldsmdfr)) {
			NBTHelper.setBoolean(stack, "saved", true);
			NBTHelper.setInteger(stack, "x", x);
			NBTHelper.setInteger(stack, "y", y);
			NBTHelper.setInteger(stack, "z", z);
		} else if (!player.isSneaking()
				&& world.getBlock(x, y, z).equals(ModBlocks.terminal)
				&& NBTHelper.getBoolean(stack, "saved")) {
			TileEntity t = world.getTileEntity(x, y, z);
			if (t != null && t instanceof TileTerminal) {
				TileTerminal tile = (TileTerminal) t;
				tile.setX(x);
				tile.setY(y);
				tile.setZ(z);

				NBTHelper.setBoolean(stack, "saved", false);
			}
		}

		return false;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list,
			boolean bool) {
		if (NBTHelper.getBoolean(stack, "saved")) {
			list.add("x: " + NBTHelper.getInt(stack, "x") + " y: "
					+ NBTHelper.getInt(stack, "y") + " z: "
					+ NBTHelper.getInt(stack, "z"));
		}
	}

}
