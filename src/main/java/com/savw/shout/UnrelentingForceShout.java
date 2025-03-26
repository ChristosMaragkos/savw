package com.savw.shout;

import com.savw.entity.projectile.ForceShockwave;
import com.savw.word.ShoutWord;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class UnrelentingForceShout extends AbstractShout{

    private UnrelentingForceShout(String name, String description, ShoutWord firstWord, ShoutWord secondWord, ShoutWord thirdWord, ResourceLocation iconLocation) {
        super(name, description, firstWord, secondWord, thirdWord, iconLocation);
    }

    public ForceShockwave getShoutEffect(Player player){
        return new ForceShockwave(player, player.getLookAngle(), player.level());
    }

    @Override
    public void useShout(Player player, Level level) {
        getShoutEffect(player).useShout(player, level);
    }
}
