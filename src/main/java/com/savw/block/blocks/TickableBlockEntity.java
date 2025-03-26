package com.savw.block.blocks;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;

public interface TickableBlockEntity {
    void tick();

    static <T extends BlockEntity> BlockEntityTicker<T> getTicker(){
        return (level, pos, state, blockEntity) -> {
            if (blockEntity instanceof TickableBlockEntity tickableBlockEntity) {
                tickableBlockEntity.tick();
            }
        };
    }
}
