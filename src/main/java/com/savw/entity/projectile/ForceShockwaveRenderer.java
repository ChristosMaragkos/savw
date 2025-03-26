package com.savw.entity.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class ForceShockwaveRenderer extends EntityRenderer<ForceShockwave, EntityRenderState> {

    private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/enderdragon/dragon_fireball.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE_LOCATION);

    public ForceShockwaveRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(EntityRenderState entityRenderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        poseStack.pushPose();
        poseStack.scale(2.0F, 2.0F, 2.0F);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        PoseStack.Pose pose = poseStack.last();
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RENDER_TYPE);
        vertex(vertexConsumer, pose, i, 0.0F, 0, 0, 1);
        vertex(vertexConsumer, pose, i, 1.0F, 0, 1, 1);
        vertex(vertexConsumer, pose, i, 1.0F, 1, 1, 0);
        vertex(vertexConsumer, pose, i, 0.0F, 1, 0, 0);
        poseStack.popPose();
        super.render(entityRenderState, poseStack, multiBufferSource, i);
    }

    private static void vertex(VertexConsumer vertexConsumer, PoseStack.Pose pose, int i, float f, int j, int k, int l) {
        vertexConsumer.addVertex(pose, f - 0.5F, j - 0.25F, 0.0F)
                .setColor(-1)
                .setUv(k, l)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(i)
                .setNormal(pose, 0.0F, 1.0F, 0.0F);
    }

    @Override
    public @NotNull EntityRenderState createRenderState() {
        return new EntityRenderState();
    }
}
