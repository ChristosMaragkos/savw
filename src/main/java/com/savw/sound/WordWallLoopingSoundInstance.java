package com.savw.sound;

import com.savw.block.blocks.WordWallBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

@Environment(EnvType.CLIENT)
public class WordWallLoopingSoundInstance extends AbstractTickableSoundInstance {

    private final WordWallBlockEntity wordWallBlockEntity;

    public WordWallLoopingSoundInstance(WordWallBlockEntity wordWallBlockEntity, SoundEvent soundEvent, SoundSource soundSource, RandomSource randomSource) {
        super(soundEvent, soundSource, randomSource);

        this.wordWallBlockEntity = wordWallBlockEntity;
        this.volume = 1.0f;
        this.pitch = 1.0f;
        this.looping = true;
        this.setPositionToBlockEntity();
    }

    @Override
    public boolean canStartSilent() {
        return this.wordWallBlockEntity.closestPlayer != null;
    }

    @Override
    public void tick() {

        if (this.wordWallBlockEntity.isRemoved() || this.wordWallBlockEntity.closestPlayer == null) {
            this.stop();
            return;
        }

        this.volume = (float)(3 / this.wordWallBlockEntity.closestPlayer.distanceToSqr(this.wordWallBlockEntity.getBlockPos().getX(), this.wordWallBlockEntity.getBlockPos().getY(), this.wordWallBlockEntity.getBlockPos().getZ()));
        if (this.volume > 1.8f) {
            this.volume = 1.8f;
        }

    }

    private void setPositionToBlockEntity() {
        this.x = this.wordWallBlockEntity.getBlockPos().getX();
        this.y = this.wordWallBlockEntity.getBlockPos().getY();
        this.z = this.wordWallBlockEntity.getBlockPos().getZ();
    }



}
