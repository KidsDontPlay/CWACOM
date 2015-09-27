package mrriegel.cwacom.render;

import org.lwjgl.opengl.GL11;

import mrriegel.cwacom.Reference;
import mrriegel.cwacom.tile.TileFldsmdfr;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class FldsmdfrItemRenderer implements IItemRenderer {

	private ModelFldsmdfr model;
	private final ResourceLocation texture1 = new ResourceLocation(
			Reference.MOD_ID + ":" + "textures/models/fldsmdfr_active.png");

	public FldsmdfrItemRenderer() {
		model = new ModelFldsmdfr();
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		// return type == ItemRenderType.INVENTORY;
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if (type == ItemRenderType.INVENTORY) {
			GL11.glPushMatrix();
			// GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z +
			// 0.5F);
			Minecraft.getMinecraft().renderEngine.bindTexture(texture1);
			GL11.glPushMatrix();
			GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(0.67F, 0.67F, 0.67F);
			GL11.glTranslatef(0.0F, -0.47F, 0.0F);
			model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		} else if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glPushMatrix();
			Minecraft.getMinecraft().renderEngine.bindTexture(texture1);
			GL11.glPushMatrix();
			GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.6F, -0.6F, 0.6F);
			model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		} else if (type == ItemRenderType.EQUIPPED) {
			GL11.glPushMatrix();
			Minecraft.getMinecraft().renderEngine.bindTexture(texture1);
			GL11.glPushMatrix();
			GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.5F, -0.6F, -0.5F);
			model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		} else if (type == ItemRenderType.ENTITY) {
			GL11.glPushMatrix();
			Minecraft.getMinecraft().renderEngine.bindTexture(texture1);
			GL11.glPushMatrix();
			GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
			model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();
			GL11.glPopMatrix();

		}
	}

}
