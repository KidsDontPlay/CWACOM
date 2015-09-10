package mrriegel.cwacom.packet;

import mrriegel.cwacom.tile.TileTerminal;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class TerminalPacketHandler implements
		IMessageHandler<TerminalPacket, IMessage> {

	@Override
	public IMessage onMessage(TerminalPacket message, MessageContext ctx) {
		try {
			TileTerminal tile = (TileTerminal) ctx.getServerHandler().playerEntity.worldObj
					.getTileEntity(message.x, message.y, message.z);
			tile.setCount(message.stack);
			ctx.getServerHandler().playerEntity.worldObj.markBlockForUpdate(
					message.x, message.y, message.z);
		} catch (Exception e) {
		}

		return null;
	}

}
