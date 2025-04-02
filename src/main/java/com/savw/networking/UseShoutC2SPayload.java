package com.savw.networking;

import com.savw.SkyAboveVoiceWithin;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record UseShoutC2SPayload(int entityId, int wordsUsed) implements CustomPacketPayload {

    public static final ResourceLocation USE_SHOUT_PAYLOAD_ID = ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "use_shout_payload");
    public static final Type<UseShoutC2SPayload> USE_SHOUT_PAYLOAD_TYPE = new Type<>(USE_SHOUT_PAYLOAD_ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, UseShoutC2SPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, UseShoutC2SPayload::entityId, ByteBufCodecs.INT, UseShoutC2SPayload::wordsUsed ,UseShoutC2SPayload::new);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return USE_SHOUT_PAYLOAD_TYPE;
    }
}
