package mrriegel.cwacom.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import scala.util.control.TailCalls.Cont;
import mrriegel.cwacom.CWACOM;
import mrriegel.cwacom.packet.PacketHandler;
import mrriegel.cwacom.packet.TerminalPacket;
import mrriegel.cwacom.reference.Reference;
import mrriegel.cwacom.tile.TileTerminal;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

public class GuiTerminal extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation(
			Reference.MOD_ID + ":" + "textures/gui/terminal.png");

	TileTerminal tile;
	GuiButton but;
	int index;

	public GuiTerminal(ContainerTerminal containerTerminal) {
		super(containerTerminal);
		this.tile = containerTerminal.tile;

	}

	@Override
	public void initGui() {
		super.initGui();
		but = new GuiButton(0, 70 + guiLeft, 20 + guiTop, 70, 20, "Next");
		this.buttonList.add(but);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
		case 0:
			System.out.println("nana");
			PacketHandler.INSTANCE.sendToServer(new TerminalPacket(
					CWACOM.foodList.get(index), tile.xCoord, tile.yCoord,
					tile.zCoord));
			break;
		}

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {

		fontRendererObj.drawString("Terminal", 8, 6, 4210752);
		fontRendererObj.drawString(tile.getStack().getDisplayName(),
				but.xPosition, but.yPosition + 30, 4210752);
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
		tessellator.setColorRGBA(28, 134, 235, 255);
		double s = -47.D / 100000.D * tile.getEn().getEnergyStored() + 65.D;
		tessellator.addVertex(18, s, 0);// lo
		tessellator.addVertex(18, 65.D, 0);// lu
		tessellator.addVertex(30, 65.D, 0);// ru
		tessellator.addVertex(30, s, 0);// ro
		tessellator.draw();

		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		if (func_146978_c(18, 18, 12, 47, param1, param2)) {
			List list = new ArrayList();
			int k = (width - xSize) / 2;
			int l = (height - ySize) / 2;
			list.add(tile.getEn().getEnergyStored() + " / "
					+ tile.getEn().getMaxEnergyStored());
			this.drawHoveringText(list, param1 - k, param2 - l, fontRendererObj);
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
