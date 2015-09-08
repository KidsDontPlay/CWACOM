package mrriegel.cwacom.packet;

import mrriegel.cwacom.reference.Reference;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {
	public static final SimpleNetworkWrapper INSTANCE = new SimpleNetworkWrapper(
			Reference.MOD_ID);

	public static void init() {
		int id = 0;

		INSTANCE.registerMessage(TerminalPacketHandler.class,
				TerminalPacket.class, id++, Side.SERVER);
	}
}
