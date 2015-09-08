package mrriegel.cwacom.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class TerminalPacketHandler implements IMessageHandler<TerminalPacket, IMessage>{

	@Override
	public IMessage onMessage(TerminalPacket message, MessageContext ctx) {
		
		return null;
	}

}
