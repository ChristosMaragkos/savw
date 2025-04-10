package com.savw.entity.projectile.death;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.savw.entity.projectile.DeathShockwave;
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
public class DeathShockwaveRenderer extends EntityRenderer<DeathShockwave, DeathShockwaveRenderState> {

    private final DeathShockwaveModel model;

    private static final ResourceLocation TEXTURE_LOCATION = withModId("textures/entity/draining_shockwave/draining_shockwave.png");
    private static final RenderType RENDER_TYPE = RenderType.entityTranslucentEmissive(getTextureLocation());

    public DeathShockwaveRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new DeathShockwaveModel(context.bakeLayer(SHOCKWAVE_LAYER));
    }

    public static ResourceLocation getTextureLocation() {
        return TEXTURE_LOCATION;
    }

    public static RenderType getRenderType() {
        return RENDER_TYPE;
    }

    @Override
    public void render(@NotNull DeathShockwaveRenderState deathShockwaveRenderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(-deathShockwaveRenderState.yRot + 180f));
        poseStack.mulPose(Axis.XP.rotationDegrees(deathShockwaveRenderState.xRot));
        poseStack.scale(1f,1f,1f);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(getRenderType());
        this.model.setupAnim(deathShockwaveRenderState);
        this.model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(deathShockwaveRenderState, poseStack, multiBufferSource, i);
    }

    @Override
    public @NotNull DeathShockwaveRenderState createRenderState() {
        return new DeathShockwaveRenderState();
    }

    @Override
    public void extractRenderState(@NotNull DeathShockwave entity, @NotNull DeathShockwaveRenderState reusedState, float partialTick) {
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
