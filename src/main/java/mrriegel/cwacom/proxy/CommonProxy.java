package mrriegel.cwacom.proxy;

import mrriegel.cwacom.gui.ContainerFldsmdfr;
import mrriegel.cwacom.gui.ContainerTerminal;
import mrriegel.cwacom.gui.GuiFldsmdfr;
import mrriegel.cwacom.gui.GuiTerminal;
import mrriegel.cwacom.tile.TileFldsmdfr;
import mrriegel.cwacom.tile.TileTerminal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {
	public static final int TERMINAL = 0;
	public static final int F = 1;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		switch (ID) {
		case TERMINAL:
			return new ContainerTerminal(player, player.inventory,
					(TileTerminal) world.getTileEntity(x, y, z));
		case F:
			return new ContainerFldsmdfr(player, player.inventory,
					(TileFldsmdfr) world.getTileEntity(x, y, z));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		switch (ID) {
		case TERMINAL:
			return new GuiTerminal(new ContainerTerminal(player,
					player.inventory, (TileTerminal) world.getTileEntity(x, y,
							z)));
		case F:
			return new GuiFldsmdfr(new ContainerFldsmdfr(player,
					player.inventory, (TileFldsmdfr) world.getTileEntity(x, y,
							z)));
		}
		return null;
	}

}
