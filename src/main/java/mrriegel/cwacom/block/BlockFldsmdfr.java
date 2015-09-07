package mrriegel.cwacom.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrriegel.cwacom.CreativeTab;
import mrriegel.cwacom.reference.Reference;
import mrriegel.cwacom.tile.TileFldsmdfr;
import mrriegel.cwacom.tile.TileTerminal;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class BlockFldsmdfr extends BlockContainer {
	@SideOnly(Side.CLIENT)
	private IIcon[] icons = new IIcon[6];

	public BlockFldsmdfr() {
		super(Material.iron);
		this.setCreativeTab(CreativeTab.tab1);
		this.setBlockName(Reference.MOD_ID + ":" + "fldsmdfr");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		for (int i = 0; i < 6; i++) {
			this.icons[i] = reg.registerIcon(Reference.MOD_ID + ":"
					+ "fldsmdfr");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return this.icons[side];
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileFldsmdfr();
	}

}
