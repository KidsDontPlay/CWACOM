package mrriegel.cwacom.init;

import mrriegel.cwacom.Reference;
import mrriegel.cwacom.block.BlockFldsmdfr;
import mrriegel.cwacom.block.BlockTerminal;
import mrriegel.cwacom.tile.TileFldsmdfr;
import mrriegel.cwacom.tile.TileTerminal;
import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {

	public static final Block terminal = new BlockTerminal();
	public static final Block fldsmdfr = new BlockFldsmdfr();

	public static void init() {
		GameRegistry.registerBlock(terminal, "terminal");
		GameRegistry.registerBlock(fldsmdfr, "fldsmdfr");

		GameRegistry.registerTileEntity(TileFldsmdfr.class, "tileFldsmdfr");
		GameRegistry.registerTileEntity(TileTerminal.class, "tileTerminal");

	}

}
