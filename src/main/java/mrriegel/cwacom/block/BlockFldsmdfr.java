package mrriegel.cwacom.block;

import java.util.Random;

import mrriegel.cwacom.CWACOM;
import mrriegel.cwacom.CreativeTab;
import mrriegel.cwacom.Reference;
import mrriegel.cwacom.init.ModBlocks;
import mrriegel.cwacom.init.ModItems;
import mrriegel.cwacom.proxy.CommonProxy;
import mrriegel.cwacom.tile.TileFldsmdfr;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFldsmdfr extends BlockContainer {
	@SideOnly(Side.CLIENT)
	private IIcon front;

	public BlockFldsmdfr() {
		super(Material.iron);
		this.setCreativeTab(CreativeTab.tab1);
		this.setHardness(3.0F);
		this.setResistance(8.0F);
		this.setBlockName(Reference.MOD_ID + ":" + "fldsmdfr");
		// this.setBlockBounds(0, -1, 0, 1, 1, 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(Reference.MOD_ID + ":"
				+ "fldsmdfr_side");
		this.front = reg
				.registerIcon(Reference.MOD_ID + ":" + "fldsmdfr_front");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side == 1 ? this.blockIcon : (side == 0 ? this.blockIcon
				: (side != meta ? this.blockIcon : this.front));
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileFldsmdfr();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int p_149727_6_, float p_149727_7_,
			float p_149727_8_, float p_149727_9_) {
		if (player.getHeldItem() != null
				&& player.getHeldItem().getItem().equals(Items.water_bucket)) {
			if (world.isRemote)
				return true;
			TileFldsmdfr tile = (TileFldsmdfr) world.getTileEntity(x, y, z);
			if (player.capabilities.isCreativeMode) {
				tile.fill(ForgeDirection.UNKNOWN, new FluidStack(
						FluidRegistry.WATER, 1000), true);
				return true;

			}
			if (!player.capabilities.isCreativeMode
					&& tile.canFill(ForgeDirection.UNKNOWN,
							FluidRegistry.WATER, 1000)) {
				player.inventory.setInventorySlotContents(
						player.inventory.currentItem, (ItemStack) null);
				player.inventory.addItemStackToInventory(new ItemStack(
						Items.bucket));
				tile.fill(ForgeDirection.UNKNOWN, new FluidStack(
						FluidRegistry.WATER, 1000), true);
				return true;
			}
			return false;
		}
		if (player.getHeldItem() == null
				|| !player.getHeldItem().getItem().equals(ModItems.rc)) {
			player.openGui(CWACOM.instance, CommonProxy.F, world, x, y, z);
			return true;
		}

		return false;
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_,
			int p_149650_3_) {
		return Item.getItemFromBlock(ModBlocks.fldsmdfr);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase player, ItemStack stack) {
		setDirection(world, x, y, z, player, stack);
		if (world.isAirBlock(x, y + 1, z) && world.setBlock(x, y + 1, z, this)) {
			setDirection(world, x, y + 1, z, player, stack);
			world.setBlockToAir(x, y, z);
		} else {
			world.setBlockToAir(x, y, z);
			((EntityPlayer) player).inventory
					.addItemStackToInventory(new ItemStack(this));
		}

	}

	private void setDirection(World world, int x, int y, int z,
			EntityLivingBase player, ItemStack stack) {
		TileFldsmdfr tile = (TileFldsmdfr) world.getTileEntity(x, y, z);
		int l = MathHelper
				.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		if (l == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
			tile.setAngle(0F);
			world.markBlockForUpdate(x, y, z);
		}

		if (l == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
			tile.setAngle(90F);
			world.markBlockForUpdate(x, y, z);
		}

		if (l == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
			tile.setAngle(180F);
			world.markBlockForUpdate(x, y, z);
		}

		if (l == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
			tile.setAngle(270F);
			world.markBlockForUpdate(x, y, z);
		}
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

}
