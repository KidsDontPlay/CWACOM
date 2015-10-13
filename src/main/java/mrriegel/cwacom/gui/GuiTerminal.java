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
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

public class GuiTerminal extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation(
			Reference.MOD_ID + ":" + "textures/gui/terminal.png");

	TileTerminal tile;
	TileFldsmdfr tf;
	GuiButton lefF, rigF, lefM, rigM;

	public GuiTerminal(ContainerTerminal containerTerminal) {
		super(containerTerminal);
		this.tile = containerTerminal.tile;
		tf = tile.getTf();

	}

	@Override
	public void initGui() {
		super.initGui();
		lefF = new GuiButton(0, 65 + guiLeft, 18 + guiTop, 20, 20, "<");
		this.buttonList.add(lefF);
		rigF = new GuiButton(1, 130 + guiLeft, 18 + guiTop, 20, 20, ">");
		this.buttonList.add(rigF);
		lefM = new GuiButton(2, 65 + guiLeft, 48 + guiTop, 20, 20, "<");
		this.buttonList.add(lefM);
		rigM = new GuiButton(3, 130 + guiLeft, 48 + guiTop, 20, 20, ">");
		this.buttonList.add(rigM);

		if (tile.getRate() == 0)
			lefM.enabled = false;
		else
			lefM.enabled = true;
		if (tile.getRate() == 10)
			rigM.enabled = false;
		else
			rigM.enabled = true;
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
		case 0:
			if (tile.getCount() <= 0)
				tile.setCount(CWACOM.foodList.size() - 1);
			else
				tile.setCount(tile.getCount() - 1);
			PacketHandler.INSTANCE
					.sendToServer(new TerminalPacket(tile.getCount(),
							tile.xCoord, tile.yCoord, tile.zCoord, "food"));
			break;

		case 1:
			if (tile.getCount() >= CWACOM.foodList.size() - 1)
				tile.setCount(0);
			else
				tile.setCount(tile.getCount() + 1);
			PacketHandler.INSTANCE
					.sendToServer(new TerminalPacket(tile.getCount(),
							tile.xCoord, tile.yCoord, tile.zCoord, "food"));
			break;
		case 2:
			if (tile.getRate() <= 0) {
				tile.setRate(0);
				break;
			}
			tile.setRate(tile.getRate() - 1);
			PacketHandler.INSTANCE.sendToServer(new TerminalPacket(tile
					.getRate(), tile.xCoord, tile.yCoord, tile.zCoord, "rate"));
			break;
		case 3:
			if (tile.getRate() >= 10) {
				tile.setRate(10);
				break;
			}
			tile.setRate(tile.getRate() + 1);
			PacketHandler.INSTANCE.sendToServer(new TerminalPacket(tile
					.getRate(), tile.xCoord, tile.yCoord, tile.zCoord, "rate"));
			break;

		}
		if (tile.getRate() == 0)
			lefM.enabled = false;
		else
			lefM.enabled = true;
		if (tile.getRate() == 10)
			rigM.enabled = false;
		else
			rigM.enabled = true;
		// System.out.println("c: "+tile.getCount()+ " r: "+tile.getRate());

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		fontRendererObj.drawString("Terminal", 8, 6, 4210752);
		ItemStack st = tile.getCount() != 0 ? CWACOM.foodList.get(tile
				.getCount()) : CWACOM.foodList.get((int) (System
				.currentTimeMillis() / 333 % (CWACOM.foodList.size() - 1)) + 1);

		RenderItem r = new RenderItem();
		r.renderItemAndEffectIntoGUI(fontRendererObj, mc.renderEngine, st, 100,
				20);

		String rat = String.valueOf((int) Math.pow(2, (10 - tile.getRate())))
				+ " s";
		if (tile.getRate() == 0)
			rat = "Off";
		fontRendererObj.drawString(rat,
				109 - fontRendererObj.getStringWidth(rat) / 2, 54, 0x000000);
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
			double he = 65.D;
			if (tf.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid != null)
				he = -47.D
						/ 16000.D
						* tf.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid.amount
						+ 65.D;
			tessellator.addVertex(42, 18, 0);// lo
			tessellator.addVertex(42, he, 0);// lu
			tessellator.addVertex(54, he, 0);// ru
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

		if (func_146978_c(100, 20, 16, 16, param1, param2)) {
			List list = new ArrayList();
			int k = (width - xSize) / 2;
			int l = (height - ySize) / 2;
			if (tile.getCount() != 0)
				list.add(st.getDisplayName());
			else
				list.add("Random");
			GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			this.drawHoveringText(list, param1 - k, param2 - l, fontRendererObj);
			GL11.glPopAttrib();
			GL11.glPopAttrib();
		} else if (func_146978_c(1, 1, 50, 12, param1, param2) && tf != null) {
			List list = new ArrayList();
			int k = (width - xSize) / 2;
			int l = (height - ySize) / 2;
			list.add("x: " + tf.xCoord + " y: " + tf.yCoord + " z: "
					+ tf.zCoord);
			GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			this.drawHoveringText(list, param1 - k, param2 - l, fontRendererObj);
			GL11.glPopAttrib();
			GL11.glPopAttrib();
		} else if (func_146978_c(18, 18, 12, 47, param1, param2)) {
			List list = new ArrayList();
			int k = (width - xSize) / 2;
			int l = (height - ySize) / 2;
			list.add(tile.getEn().getEnergyStored() + " RF / "
					+ tile.getEn().getMaxEnergyStored() + " RF");
			GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			this.drawHoveringText(list, param1 - k, param2 - l, fontRendererObj);
			GL11.glPopAttrib();
			GL11.glPopAttrib();
		} else if (func_146978_c(42, 18, 12, 47, param1, param2)) {
			List list = new ArrayList();
			int k = (width - xSize) / 2;
			int l = (height - ySize) / 2;
			if (tf != null)
				if (tf.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid != null)
					list.add(tf.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid.amount
							+ " mB / "
							+ tf.getTankInfo(ForgeDirection.UNKNOWN)[0].capacity
							+ " mB");
				else
					list.add("0 mB"
							+ " / "
							+ tf.getTankInfo(ForgeDirection.UNKNOWN)[0].capacity
							+ " mB");
			else
				list.add("No connected Fldsmdfr");
			GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			this.drawHoveringText(list, param1 - k, param2 - l, fontRendererObj);
			GL11.glPopAttrib();
			GL11.glPopAttrib();
		}
		if (tile.getRate() == 0 || tile.getEnergyStored(null) <= 0)
			return;

		long num = (long) Math.pow(2, (10 - tile.getRate())) * 20;
		long x = (num - tile.getCooldown());
		float ff = (float) x / ((float) num / 47F) + 18;
		if (ff < 18)
			ff = (ff % 18) + 47;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		tessellator.startDrawing(GL11.GL_QUADS);
		tessellator.setColorRGBA(0, 290 - (int) (ff * 4.9F), 102, 255);
		tessellator.addVertex(158, ff, 0);// lo
		tessellator.addVertex(158, 65, 0);// lu
		tessellator.addVertex(165, 65, 0);// ru
		tessellator.addVertex(165, ff, 0);// ro
		tessellator.draw();

		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

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
