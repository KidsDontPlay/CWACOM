package mrriegel.cwacom.packet;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class TerminalPacket implements IMessage {

	String kind;
	int x, y, z, number;

	public TerminalPacket() {
	}

	public TerminalPacket(int number, int x, int y, int z, String kind) {
		this.number = number;
		this.x = x;
		this.y = y;
		this.z = z;
		this.kind = kind;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.number = buf.readInt();
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.kind = ByteBufUtils.readUTF8String(buf);

	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(number);
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		ByteBufUtils.writeUTF8String(buf, kind);
	}

}
