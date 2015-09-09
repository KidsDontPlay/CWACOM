package mrriegel.cwacom.block;

import mrriegel.cwacom.CWACOM;
import mrriegel.cwacom.CreativeTab;
import mrriegel.cwacom.Reference;
import mrriegel.cwacom.init.ModItems;
import mrriegel.cwacom.proxy.CommonProxy;
import mrriegel.cwacom.tile.TileTerminal;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

;
public class BlockTerminal extends BlockContainer {
	@SideOnly(Side.CLIENT)
	private IIcon front;

	public BlockTerminal() {
		super(Material.iron);
		this.setCreativeTab(CreativeTab.tab1);
		this.setBlockName(Reference.MOD_ID + ":" + "terminal");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(Reference.MOD_ID + ":"
				+ "terminal_side");
		this.front = reg
				.registerIcon(Reference.MOD_ID + ":" + "terminal_front");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side == 1 ? this.blockIcon : (side == 0 ? this.blockIcon
				: (side != meta ? this.blockIcon : this.front));
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileTerminal();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int p_149727_6_, float p_149727_7_,
			float p_149727_8_, float p_149727_9_) {
		if (player.getHeldItem() == null
				|| !player.getHeldItem().getItem().equals(ModItems.rc)) {
			player.openGui(CWACOM.instance, CommonProxy.TERMINAL, world, x, y,
					z);
			return true;
		}
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase player, ItemStack stack) {
		int l = MathHelper
				.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		if (l == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}

		if (l == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}

		if (l == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}

		if (l == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}

	}

}
