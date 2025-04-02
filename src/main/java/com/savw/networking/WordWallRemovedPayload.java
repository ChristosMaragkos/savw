package com.savw.networking;

import com.savw.SkyAboveVoiceWithin;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record WordWallRemovedPayload(boolean whyIsThisHere) implements CustomPacketPayload {

    public static final Type<WordWallRemovedPayload> WORD_WALL_REMOVED_PAYLOAD_TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "word_wall_removed_payload"));

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return WORD_WALL_REMOVED_PAYLOAD_TYPE;
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, WordWallRemovedPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            WordWallRemovedPayload::whyIsThisHere,
            WordWallRemovedPayload::new
    );
}
