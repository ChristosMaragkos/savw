package com.savw.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

/// # EtherealMobEffect
/// Represents the Ethereal effect in the game.
/// The effect is used to give the player the same overlay as Charged Creepers.
/// @see com.savw.shout.BecomeEtherealShout BecomeEtherealShout
/// @see com.savw.mixin.PlayerRendererMixin PlayerRendererMixin
public class EtherealMobEffect extends MobEffect {

    protected EtherealMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xADD8E6);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(@NotNull ServerLevel level, @NotNull LivingEntity entity, int amplifier) {
        return super.applyEffectTick(level, entity, amplifier);
    }

}
