package com.savw.entity.player;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class PlayerPowerLayer extends EnergySwirlLayer<PlayerRenderState, PlayerModel> {

    private static final ResourceLocation POWER_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/creeper/creeper_armor.png");
    private final PlayerModel model;

    public PlayerPowerLayer(RenderLayerParent<PlayerRenderState, PlayerModel> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new PlayerModel(modelSet.bakeLayer(ModelLayers.PLAYER), false);
    }

    @Override
    protected boolean isPowered(@NotNull PlayerRenderState renderState) {
        return ((ShoutEffectPlayerRenderState) renderState).isEthereal;
    }

    @Override
    protected float xOffset(float tickCount) {
        return 0;
    }

    @Override
    protected @NotNull ResourceLocation getTextureLocation() {
        return POWER_LOCATION;
    }

    @Override
    protected @NotNull PlayerModel model() {
        return this.model;
    }
}
