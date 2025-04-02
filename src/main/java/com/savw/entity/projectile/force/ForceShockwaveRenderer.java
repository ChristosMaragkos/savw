package com.savw.entity.projectile.force;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.savw.SkyAboveVoiceWithin;
import com.savw.entity.projectile.ForceShockwave;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static com.savw.SkyAboveVoiceWithinClient.SHOCKWAVE_LAYER;

@Environment(EnvType.CLIENT)
public class ForceShockwaveRenderer extends EntityRenderer<ForceShockwave, ForceShockwaveRenderState> {

    private final ForceShockwaveModel model;

    private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID,
            "textures/entity/force_shockwave/force_shockwave.png");
    private static final RenderType RENDER_TYPE = RenderType.entityTranslucentEmissive(getTextureLocation());

    public ForceShockwaveRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new ForceShockwaveModel(context.bakeLayer(SHOCKWAVE_LAYER));
    }

    public static ResourceLocation getTextureLocation() {
        return TEXTURE_LOCATION;
    }

    public static RenderType getRenderType() {
        return RENDER_TYPE;
    }

    @Override
    public void render(ForceShockwaveRenderState forceShockwaveRenderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(-forceShockwaveRenderState.yRot + 180f));
        poseStack.mulPose(Axis.XP.rotationDegrees(forceShockwaveRenderState.xRot));
        poseStack.scale(1f,1f,1f);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(getRenderType());
        this.model.setupAnim(forceShockwaveRenderState);
        this.model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(forceShockwaveRenderState, poseStack, multiBufferSource, i);
    }

    @Override
    public @NotNull ForceShockwaveRenderState createRenderState() {
        return new ForceShockwaveRenderState();
    }

    @Override
    public void extractRenderState(@NotNull ForceShockwave entity, @NotNull ForceShockwaveRenderState reusedState, float partialTick) {
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
