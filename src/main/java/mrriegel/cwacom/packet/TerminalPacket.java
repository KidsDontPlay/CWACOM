package mrriegel.cwacom.packet;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class TerminalPacket implements IMessage {
	int stack;
	int x, y, z;

	public TerminalPacket() {
	}

	public TerminalPacket(int stack, int x, int y, int z) {
		this.stack = stack;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.stack = buf.readInt();
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();

	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(stack);
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}

}
