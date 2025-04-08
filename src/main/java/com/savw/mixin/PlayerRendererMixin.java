package com.savw.mixin;

import com.savw.effect.SkyAboveVoiceWithinMobEffects;
import com.savw.entity.player.PlayerPowerLayer;
import com.savw.entity.player.ShoutEffectPlayerRenderState;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerRenderState, PlayerModel> {

    public PlayerRendererMixin(EntityRendererProvider.Context context, boolean useSlimModel) {
        super(context, new PlayerModel(context.bakeLayer(useSlimModel ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER), useSlimModel), 0.5F);
        this.addLayer(new PlayerItemInHandLayer<>(this));
        this.addLayer(new ArrowLayer<>(this, context));
        this.addLayer(new Deadmau5EarsLayer(this, context.getModelSet()));
        this.addLayer(new CapeLayer(this, context.getModelSet(), context.getEquipmentAssets()));
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet()));
        this.addLayer(new WingsLayer<>(this, context.getModelSet(), context.getEquipmentRenderer()));
        this.addLayer(new ParrotOnShoulderLayer(this, context.getModelSet()));
        this.addLayer(new SpinAttackEffectLayer(this, context.getModelSet()));
        this.addLayer(new BeeStingerLayer<>(this, context));
        this.addLayer(new PlayerPowerLayer(this, context.getModelSet()));
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void injectedConstructor(EntityRendererProvider.Context context, boolean useSlimModel, CallbackInfo ci) {
        this.addLayer(new PlayerPowerLayer(this, context.getModelSet()));
    }

    @Inject(method = "createRenderState()Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;", at = @At("HEAD"), cancellable = true)
    public void injectedCreateRenderState(CallbackInfoReturnable<ShoutEffectPlayerRenderState> cir) {
        cir.setReturnValue(new ShoutEffectPlayerRenderState());
    }

    @Inject(method = "extractRenderState(Lnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;F)V", at = @At("HEAD"))
    public void injectedExtractRenderState(AbstractClientPlayer abstractClientPlayer, PlayerRenderState playerRenderState, float f, CallbackInfo ci) {
        ShoutEffectPlayerRenderState shoutEffectPlayerRenderState = (ShoutEffectPlayerRenderState) playerRenderState;
        super.extractRenderState(abstractClientPlayer, shoutEffectPlayerRenderState, f);
        shoutEffectPlayerRenderState.isEthereal = abstractClientPlayer.hasEffect(SkyAboveVoiceWithinMobEffects.ETHEREAL);
    }

}
