package com.savw.sound;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

@Environment(EnvType.CLIENT)
public class UnlockWordMovingSoundInstance extends AbstractTickableSoundInstance {

    LocalPlayer player;

    public UnlockWordMovingSoundInstance(LocalPlayer player, SoundEvent soundEvent, SoundSource soundSource, RandomSource randomSource) {
        super(soundEvent, soundSource, randomSource);

        this.player = player;
        this.volume = 1.0f;
        this.pitch = 1.0f;
        this.setPositionToPlayer();
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }

    @Override
    public void tick() {

        this.setPositionToPlayer();

    }

    private void setPositionToPlayer() {
        this.x = this.player.blockPosition().getX();
        this.y = this.player.blockPosition().getY();
        this.z = this.player.blockPosition().getZ();
    }



}
