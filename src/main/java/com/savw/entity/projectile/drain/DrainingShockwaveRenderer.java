package com.savw.entity.projectile.drain;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.savw.entity.projectile.DrainingShockwave;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static com.savw.SkyAboveVoiceWithin.withModId;
import static com.savw.SkyAboveVoiceWithinClient.SHOCKWAVE_LAYER;

@Environment(EnvType.CLIENT)
public class DrainingShockwaveRenderer extends EntityRenderer<DrainingShockwave, DrainingShockwaveRenderState> {

    private final DrainingShockwaveModel model;

    private static final ResourceLocation TEXTURE_LOCATION = withModId("textures/entity/draining_shockwave/draining_shockwave.png");
    private static final RenderType RENDER_TYPE = RenderType.entityTranslucentEmissive(getTextureLocation());

    public DrainingShockwaveRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new DrainingShockwaveModel(context.bakeLayer(SHOCKWAVE_LAYER));
    }

    public static ResourceLocation getTextureLocation() {
        return TEXTURE_LOCATION;
    }

    public static RenderType getRenderType() {
        return RENDER_TYPE;
    }

    @Override
    public void render(@NotNull DrainingShockwaveRenderState drainingShockwaveRenderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(-drainingShockwaveRenderState.yRot + 180f));
        poseStack.mulPose(Axis.XP.rotationDegrees(drainingShockwaveRenderState.xRot));
        poseStack.scale(1f,1f,1f);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(getRenderType());
        this.model.setupAnim(drainingShockwaveRenderState);
        this.model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(drainingShockwaveRenderState, poseStack, multiBufferSource, i);
    }

    @Override
    public @NotNull DrainingShockwaveRenderState createRenderState() {
        return new DrainingShockwaveRenderState();
    }

    @Override
    public void extractRenderState(@NotNull DrainingShockwave entity, @NotNull DrainingShockwaveRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);

        // Get velocity
        double vx = entity.getDeltaMovement().x;
        double vy = entity.getDeltaMovement().y;
        double vz = entity.getDeltaMovement().z;

        // Calculate yaw (horizontal rotation)
        reusedState.yRot = (float) Math.toDegrees(Math.atan2(-vx, vz));

        // Calculate pitch (vertical rotation)
        double horizontalSpeed = Math.sqrt(vx * vx + vz * vz);
        reusedState.xRot = (float) Math.toDegrees(Math.atan2(vy, horizontalSpeed));
    }


}
