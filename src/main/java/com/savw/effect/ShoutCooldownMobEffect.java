package com.savw.effect;

import net.minecraft.util.ARGB;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/// # ShoutCooldownMobEffect
/// Cooldown effect given to players when they use a shout.
/// This effect is purely for visual feedback and does not affect gameplay.
public class ShoutCooldownMobEffect extends MobEffect {

    protected ShoutCooldownMobEffect() {
        super(MobEffectCategory.HARMFUL, ARGB.colorFromFloat(0f,0f,0f,0f));
    }

}
