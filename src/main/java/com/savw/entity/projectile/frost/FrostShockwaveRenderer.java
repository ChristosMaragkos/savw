package com.savw.entity.projectile.frost;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.savw.SkyAboveVoiceWithin;
import com.savw.entity.projectile.FrostShockwave;
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
public class FrostShockwaveRenderer extends EntityRenderer<FrostShockwave, FrostShockwaveRenderState> {

    private final FrostShockwaveModel model;

    private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID,
            "textures/entity/frost_shockwave/frost_shockwave.png");
    private static final RenderType RENDER_TYPE = RenderType.entityTranslucentEmissive(getTextureLocation());

    public FrostShockwaveRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new FrostShockwaveModel(context.bakeLayer(SHOCKWAVE_LAYER));
    }

    public static ResourceLocation getTextureLocation() {
        return TEXTURE_LOCATION;
    }

    public static RenderType getRenderType() {
        return RENDER_TYPE;
    }

    @Override
    public void render(FrostShockwaveRenderState frostShockwaveRenderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(-frostShockwaveRenderState.yRot + 180f));
        poseStack.mulPose(Axis.XP.rotationDegrees(frostShockwaveRenderState.xRot));
        poseStack.scale(1f,1f,1f);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(getRenderType());
        this.model.setupAnim(frostShockwaveRenderState);
        this.model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(frostShockwaveRenderState, poseStack, multiBufferSource, i);
    }

    @Override
    public @NotNull FrostShockwaveRenderState createRenderState() {
        return new FrostShockwaveRenderState();
    }

    @Override
    public void extractRenderState(@NotNull FrostShockwave entity, @NotNull FrostShockwaveRenderState reusedState, float partialTick) {
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
