package com.savw.shout;

import com.savw.word.ShoutWord;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BecomeEtherealShout extends AbstractShout{

    protected BecomeEtherealShout(String name, String description, ShoutWord firstWord, ShoutWord secondWord, ShoutWord thirdWord, ResourceLocation iconLocation) {
        super(name, description, firstWord, secondWord, thirdWord, iconLocation);
    }

    @Override
    public void useShout(Player player, Level level) {
        if (level.isClientSide) {
            return;
        }
        player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 200, 0, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 0, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 0, false, false));
    }
}
