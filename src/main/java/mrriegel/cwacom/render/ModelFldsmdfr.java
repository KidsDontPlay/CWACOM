package mrriegel.cwacom.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Fldsmdfr - wiiv
 * Created using Tabula 4.1.1
 */
public class ModelFldsmdfr extends ModelBase {
    public ModelRenderer shape1;
    public ModelRenderer leg1;
    public ModelRenderer leg2;
    public ModelRenderer leg4;
    public ModelRenderer leg1_1;
    public ModelRenderer leg2_1;
    public ModelRenderer leg3;
    public ModelRenderer leg4_1;
    public ModelRenderer leg1_2;
    public ModelRenderer leg2_2;
    public ModelRenderer leg3_1;
    public ModelRenderer leg4_2;
    public ModelRenderer leg1_3;
    public ModelRenderer leg2_3;
    public ModelRenderer leg3_2;
    public ModelRenderer leg4_3;
    public ModelRenderer leg3_3;
    public ModelRenderer button1;
    public ModelRenderer button2;
    public ModelRenderer shape1_1;
    public ModelRenderer shape1_2;

    public ModelFldsmdfr() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.leg2_2 = new ModelRenderer(this, 0, 11);
        this.leg2_2.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.leg2_2.addBox(4.5F, 14.5F, -6.5F, 2, 1, 2, 0.0F);
        this.setRotateAngle(leg2_2, -0.04363323129985824F, 0.0F, -0.04363323129985824F);
        this.leg1_3 = new ModelRenderer(this, 0, 11);
        this.leg1_3.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.leg1_3.addBox(-6.5F, 16.0F, -6.5F, 2, 1, 2, 0.0F);
        this.setRotateAngle(leg1_3, -0.04363323129985824F, 0.0F, 0.04363323129985824F);
        this.leg2_3 = new ModelRenderer(this, 0, 11);
        this.leg2_3.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.leg2_3.addBox(4.5F, 16.0F, -6.5F, 2, 1, 2, 0.0F);
        this.setRotateAngle(leg2_3, -0.04363323129985824F, 0.0F, -0.04363323129985824F);
        this.leg1 = new ModelRenderer(this, 0, 0);
        this.leg1.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.leg1.addBox(-6.0F, 14.0F, -6.0F, 1, 8, 1, 0.0F);
        this.setRotateAngle(leg1, -0.04363323129985824F, 0.0F, 0.04363323129985824F);
        this.leg1_1 = new ModelRenderer(this, 0, 11);
        this.leg1_1.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.leg1_1.addBox(-6.5F, 14.5F, -6.5F, 2, 1, 2, 0.0F);
        this.setRotateAngle(leg1_1, -0.04363323129985824F, 0.0F, 0.04363323129985824F);
        this.leg4_1 = new ModelRenderer(this, 4, 0);
        this.leg4_1.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.leg4_1.addBox(5.0F, 21.0F, 5.0F, 3, 1, 3, 0.0F);
        this.button1 = new ModelRenderer(this, 0, 11);
        this.button1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.button1.addBox(-9.5F, 6.5F, -6.5F, 2, 1, 2, 0.0F);
        this.leg3_2 = new ModelRenderer(this, 0, 11);
        this.leg3_2.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.leg3_2.addBox(-6.5F, 16.0F, 4.5F, 2, 1, 2, 0.0F);
        this.setRotateAngle(leg3_2, 0.04363323129985824F, 0.0F, 0.04363323129985824F);
        this.leg4_2 = new ModelRenderer(this, 0, 11);
        this.leg4_2.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.leg4_2.addBox(4.5F, 14.5F, 4.5F, 2, 1, 2, 0.0F);
        this.setRotateAngle(leg4_2, 0.04363323129985824F, 0.0F, -0.04363323129985824F);
        this.shape1_1 = new ModelRenderer(this, 0, 38);
        this.shape1_1.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.shape1_1.addBox(-3.0F, -12.0F, -3.0F, 6, 2, 6, 0.0F);
        this.shape1 = new ModelRenderer(this, 0, 0);
        this.shape1.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.shape1.addBox(-8.0F, -8.0F, -8.0F, 16, 22, 16, 0.0F);
        this.leg1_2 = new ModelRenderer(this, 4, 0);
        this.leg1_2.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.leg1_2.addBox(-8.0F, 21.0F, -8.0F, 3, 1, 3, 0.0F);
        this.leg4 = new ModelRenderer(this, 0, 0);
        this.leg4.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.leg4.addBox(5.0F, 14.0F, 5.0F, 1, 8, 1, 0.0F);
        this.setRotateAngle(leg4, 0.04363323129985824F, 0.0F, -0.04363323129985824F);
        this.leg4_3 = new ModelRenderer(this, 0, 11);
        this.leg4_3.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.leg4_3.addBox(4.5F, 16.0F, 4.5F, 2, 1, 2, 0.0F);
        this.setRotateAngle(leg4_3, 0.04363323129985824F, 0.0F, -0.04363323129985824F);
        this.leg3_3 = new ModelRenderer(this, 0, 0);
        this.leg3_3.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.leg3_3.addBox(-6.0F, 14.0F, 5.0F, 1, 8, 1, 0.0F);
        this.setRotateAngle(leg3_3, 0.04363323129985824F, 0.0F, 0.04363323129985824F);
        this.leg3_1 = new ModelRenderer(this, 0, 11);
        this.leg3_1.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.leg3_1.addBox(-6.5F, 14.5F, 4.5F, 2, 1, 2, 0.0F);
        this.setRotateAngle(leg3_1, 0.04363323129985824F, 0.0F, 0.04363323129985824F);
        this.button2 = new ModelRenderer(this, 48, 0);
        this.button2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.button2.addBox(8.0F, 2.0F, -3.0F, 2, 1, 6, 0.0F);
        this.leg3 = new ModelRenderer(this, 4, 0);
        this.leg3.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.leg3.addBox(-8.0F, 21.0F, 5.0F, 3, 1, 3, 0.0F);
        this.leg2 = new ModelRenderer(this, 0, 0);
        this.leg2.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.leg2.addBox(5.0F, 14.0F, -6.0F, 1, 8, 1, 0.0F);
        this.setRotateAngle(leg2, -0.04363323129985824F, 0.0F, -0.04363323129985824F);
        this.leg2_1 = new ModelRenderer(this, 4, 0);
        this.leg2_1.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.leg2_1.addBox(5.0F, 21.0F, -8.0F, 3, 1, 3, 0.0F);
        this.shape1_2 = new ModelRenderer(this, 24, 38);
        this.shape1_2.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.shape1_2.addBox(-4.0F, 12.0F, -4.0F, 8, 2, 8, 0.0F);
        this.shape1.addChild(this.shape1_1);
        this.shape1.addChild(this.shape1_2);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.leg2_2.render(f5);
        this.leg1_3.render(f5);
        this.leg2_3.render(f5);
        this.leg1.render(f5);
        this.leg1_1.render(f5);
        this.leg4_1.render(f5);
        this.button1.render(f5);
        this.leg3_2.render(f5);
        this.leg4_2.render(f5);
        this.shape1.render(f5);
        this.leg1_2.render(f5);
        this.leg4.render(f5);
        this.leg4_3.render(f5);
        this.leg3_3.render(f5);
        this.leg3_1.render(f5);
        this.button2.render(f5);
        this.leg3.render(f5);
        this.leg2.render(f5);
        this.leg2_1.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
