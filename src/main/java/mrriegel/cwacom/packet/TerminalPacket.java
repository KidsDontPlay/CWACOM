package mrriegel.cwacom.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class TerminalPacket implements IMessage {
	ItemStack stack;
	int x, y, z;

	public TerminalPacket() {
	}

	public TerminalPacket(ItemStack stack, int x, int y, int z) {
		this.stack = stack;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.stack = ByteBufUtils.readItemStack(buf);
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();

	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeItemStack(buf, stack);
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}

}
