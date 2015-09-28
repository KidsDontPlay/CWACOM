package mrriegel.cwacom.render;

import mrriegel.cwacom.Reference;
import mrriegel.cwacom.tile.TileFldsmdfr;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class FldsmdfrRenderer extends TileEntitySpecialRenderer {

	ModelFldsmdfr model;
	private final ResourceLocation texture1 = new ResourceLocation(
			Reference.MOD_ID + ":" + "textures/models/fldsmdfr_active.png");
	private final ResourceLocation texture2 = new ResourceLocation(
			Reference.MOD_ID + ":" + "textures/models/fldsmdfr_idle.png");

	public FldsmdfrRenderer() {
		model = new ModelFldsmdfr();
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z,
			float scale) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
		// GL11.glTranslatef((float) x + 0.5F, (float) y + 0.75F, (float) z +
		// 0.5F);
		// GL11.glScalef(0.5F, 0.5F, 0.5f);
		if (((TileFldsmdfr) te).isActive())
			Minecraft.getMinecraft().renderEngine.bindTexture(texture1);
		else
			Minecraft.getMinecraft().renderEngine.bindTexture(texture2);
		GL11.glPushMatrix();
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(((TileFldsmdfr) te).getAngle(), 0.0F, 1.0F, 0.0F);
		model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();

	}
}
