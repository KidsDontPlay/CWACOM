package mrriegel.cwacom.gui;

import java.util.ArrayList;
import java.util.List;

import mrriegel.cwacom.CWACOM;
import mrriegel.cwacom.Reference;
import mrriegel.cwacom.packet.PacketHandler;
import mrriegel.cwacom.packet.TerminalPacket;
import mrriegel.cwacom.tile.TileFldsmdfr;
import mrriegel.cwacom.tile.TileTerminal;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

public class GuiTerminal extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation(
			Reference.MOD_ID + ":" + "textures/gui/terminal.png");

	TileTerminal tile;
	TileFldsmdfr tf;
	GuiButton lef, rig;
	String food;

	public GuiTerminal(ContainerTerminal containerTerminal) {
		super(containerTerminal);
		this.tile = containerTerminal.tile;
		tf = tile.getTf();

	}

	@Override
	public void initGui() {
		super.initGui();
		lef = new GuiButton(0, 70 + guiLeft, 25 + guiTop, 20, 20, "<");
		this.buttonList.add(lef);
		rig = new GuiButton(1, 100 + guiLeft, 25 + guiTop, 20, 20, ">");
		this.buttonList.add(rig);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
		case 0:
			if (tile.getCount() <= 0)
				tile.setCount(CWACOM.foodList.size() - 1);
			else
				tile.setCount(tile.getCount() - 1);
			PacketHandler.INSTANCE.sendToServer(new TerminalPacket(tile
					.getCount(), tile.xCoord, tile.yCoord, tile.zCoord));
			break;

		case 1:
			if (tile.getCount() >= CWACOM.foodList.size() - 1)
				tile.setCount(0);
			else
				tile.setCount(tile.getCount() + 1);
			PacketHandler.INSTANCE.sendToServer(new TerminalPacket(tile
					.getCount(), tile.xCoord, tile.yCoord, tile.zCoord));
			break;
		}

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		fontRendererObj.drawString("Terminal", 8, 6, 4210752);
		if (tf != null)
			fontRendererObj.drawString("x: " + tf.xCoord + " y: " + tf.yCoord
					+ " z: " + tf.zCoord, 65, 12, 0x000000);
		if (tile.getCount() != 0)
			fontRendererObj.drawString(CWACOM.foodList.get(tile.getCount())
					.getDisplayName(), 62, 50, 0x000000);
		else
			fontRendererObj.drawString("RANDOM", 62, 50, 0x000000);
		fontRendererObj.drawString(
				StatCollector.translateToLocal("container.inventory"), 8,
				ySize - 96 + 2, 4210752);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawing(GL11.GL_QUADS);
		tessellator.setColorRGBA(166, 166, 166, 255);
		double s = -47.D / 200000.D * tile.getEn().getEnergyStored() + 65.D;
		tessellator.addVertex(18, 18, 0);// lo
		tessellator.addVertex(18, s, 0);// lu
		tessellator.addVertex(30, s, 0);// ru
		tessellator.addVertex(30, 18, 0);// ro
		tessellator.draw();

		if (tf != null) {
			tessellator.startDrawing(GL11.GL_QUADS);
			tessellator.setColorRGBA(166, 166, 166, 255);
			double st = 65.D;
			if (tf.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid != null)
				st = -47.D
						/ 8000.D
						* tf.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid.amount
						+ 65.D;
			tessellator.addVertex(42, 18, 0);// lo
			tessellator.addVertex(42, st, 0);// lu
			tessellator.addVertex(54, st, 0);// ru
			tessellator.addVertex(54, 18, 0);// ro
			tessellator.draw();
		} else {
			tessellator.startDrawing(GL11.GL_QUADS);
			tessellator.setColorRGBA(166, 166, 166, 255);
			tessellator.addVertex(42, 18, 0);// lo
			tessellator.addVertex(42, 65, 0);// lu
			tessellator.addVertex(54, 65, 0);// ru
			tessellator.addVertex(54, 18, 0);// ro
			tessellator.draw();
		}
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		if (func_146978_c(18, 18, 12, 47, param1, param2)) {
			List list = new ArrayList();
			int k = (width - xSize) / 2;
			int l = (height - ySize) / 2;
			list.add(tile.getEn().getEnergyStored() + " / "
					+ tile.getEn().getMaxEnergyStored());
			GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			this.drawHoveringText(list, param1 - k, param2 - l, fontRendererObj);
			GL11.glPopAttrib();
			GL11.glPopAttrib();
		}
		if (func_146978_c(42, 18, 12, 47, param1, param2)) {
			List list = new ArrayList();
			int k = (width - xSize) / 2;
			int l = (height - ySize) / 2;
			if (tf != null)
				if (tf.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid != null)
					list.add(tf.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid.amount
							+ " / "
							+ tf.getTankInfo(ForgeDirection.UNKNOWN)[0].capacity);
				else
					list.add("0"
							+ " / "
							+ tf.getTankInfo(ForgeDirection.UNKNOWN)[0].capacity);
			else
				list.add("No connected Fldsmdfr");
			GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			this.drawHoveringText(list, param1 - k, param2 - l, fontRendererObj);
			GL11.glPopAttrib();
			GL11.glPopAttrib();
		}

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_,
			int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(texture);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
	}

}
