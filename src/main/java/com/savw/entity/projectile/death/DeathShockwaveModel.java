package com.savw.entity.projectile.death;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.NotNull;

public class DeathShockwaveModel extends EntityModel<DeathShockwaveRenderState> {
    protected DeathShockwaveModel(ModelPart root) {
        super(root, RenderType::entityTranslucent);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition bb_main = partDefinition.addOrReplaceChild("bb_main",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-4.0F, -13.0F, -8.0F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 15)
                        .addBox(4.0F, -14.0F, -7.0F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(6, 15)
                        .addBox(-6.0F, -14.0F, -7.0F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 9)
                        .addBox(-4.0F, -15.0F, -7.0F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 12)
                        .addBox(-4.0F, -5.0F, -7.0F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 12)
                        .addBox(-4.0F, -5.0F, -7.0F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 9)
                        .addBox(-4.0F, -15.0F, -7.0F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(6, 15)
                        .addBox(-6.0F, -14.0F, -7.0F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 15)
                        .addBox(4.0F, -14.0F, -7.0F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0)
                        .addBox(-4.0F, -13.0F, -8.0F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(-3, 10)
                        .addBox(-5.0F, -6.0F, -6.0F, 10.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(-3, 7)
                        .addBox(-5.0F, -16.0F, -6.0F, 10.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(3, 13)
                        .addBox(-7.0F, -15.0F, -6.0F, 4.0F, 12.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(-3, 13)
                        .addBox(3.0F, -15.0F, -6.0F, 4.0F, 12.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(-4, 7)
                        .addBox(-6.0F, -17.0F, -3.0F, 12.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(2, 13)
                        .addBox(-8.0F, -16.0F, -3.0F, 6.0F, 14.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(-4, 10)
                        .addBox(-6.0F, -7.0F, -3.0F, 12.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(-4, 13)
                        .addBox(2.0F, -16.0F, -3.0F, 6.0F, 14.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(-3, 7)
                        .addBox(-5.0F, -16.0F, 0.0F, 10.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(3, 13)
                        .addBox(-7.0F, -15.0F, 0.0F, 4.0F, 12.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(-3, 10)
                        .addBox(-5.0F, -6.0F, 0.0F, 10.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(-3, 13)
                        .addBox(3.0F, -15.0F, 0.0F, 4.0F, 12.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(-4, 7)
                        .addBox(-6.0F, -17.0F, 1.0F, 12.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(2, 13)
                        .addBox(-8.0F, -16.0F, 1.0F, 6.0F, 14.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(-4, 10)
                        .addBox(-6.0F, -7.0F, 1.0F, 12.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(-4, 13)
                        .addBox(2.0F, -16.0F, 1.0F, 6.0F, 14.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(8, 11)
                        .addBox(7.0F, -2.0F, 4.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(9, 12)
                        .addBox(-8.0F, -2.0F, 4.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(8, 10)
                        .addBox(-7.0F, -17.0F, 4.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(7, 9)
                        .addBox(5.0F, -17.0F, 4.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(7, 9)
                        .addBox(1.0F, -16.0F, 4.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    public void setupAnim(@NotNull DeathShockwaveRenderState drainingShockwaveRenderState) {
        super.setupAnim(drainingShockwaveRenderState);
    }
}
