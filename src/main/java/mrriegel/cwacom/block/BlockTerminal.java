package mrriegel.cwacom.block;

import mrriegel.cwacom.CWACOM;
import mrriegel.cwacom.CreativeTab;
import mrriegel.cwacom.init.ModItems;
import mrriegel.cwacom.proxy.CommonProxy;
import mrriegel.cwacom.reference.Reference;
import mrriegel.cwacom.tile.TileTerminal;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTerminal extends BlockContainer {
	@SideOnly(Side.CLIENT)
	private IIcon[] icons = new IIcon[6];

	public BlockTerminal() {
		super(Material.iron);
		this.setCreativeTab(CreativeTab.tab1);
		this.setBlockName(Reference.MOD_ID + ":" + "terminal");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		for (int i = 0; i < 6; i++) {
			this.icons[i] = reg.registerIcon(Reference.MOD_ID + ":"
					+ "terminal");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return this.icons[side];
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileTerminal();
	}

	@Override
	public boolean onBlockActivated(World world, int x,
			int y, int z, EntityPlayer player,
			int p_149727_6_, float p_149727_7_, float p_149727_8_,
			float p_149727_9_) {
		if(player.getHeldItem()==null||!player.getHeldItem().getItem().equals(ModItems.rc)){
			player.openGui(CWACOM.instance, CommonProxy.TERMINAL, world, x, y, z);
			return true;
		}
		return false;
	}
}
