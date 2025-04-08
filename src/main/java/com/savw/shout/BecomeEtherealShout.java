package com.savw.shout;

import com.savw.effect.SkyAboveVoiceWithinMobEffects;
import com.savw.word.ShoutWord;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/// # BecomeEtherealShout
/// Represents the Become Ethereal shout in the game.
/// This shout grants the player invulnerability and invisibility for a short duration.
/// While in this form, the player also cannot deal damage and glows.
/// @see com.savw.shout.Shouts#BECOME_ETHEREAL
/// @see com.savw.word.Words#Feim
/// @see com.savw.word.Words#Zii
/// @see com.savw.word.Words#Gron
public final class BecomeEtherealShout extends AbstractShout{

    private BecomeEtherealShout(String name, String description, ShoutWord firstWord, ShoutWord secondWord, ShoutWord thirdWord, ResourceLocation iconLocation) {
        super(name, description, firstWord, secondWord, thirdWord, iconLocation);
    }

    @Override
    public void useShout(Player player, Level level, int wordsUsed) {
        if (level.isClientSide) {
            return;
        }
        int duration = 60 * wordsUsed;
        player.addEffect(new MobEffectInstance(SkyAboveVoiceWithinMobEffects.ETHEREAL, duration, 0, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, duration, 255, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, duration, 0, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, duration, 255, false, false));
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BREEZE_WIND_CHARGE_BURST, player.getSoundSource(), 3.0F, 1f);
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.CLOUD,
                    player.getX(),
                    player.getY() + 1.0D,
                    player.getZ(),
                    50 * wordsUsed,
                    0.5D,
                    0.5D,
                    0.5D,
                    0.1D
            );
        }
    }
}
