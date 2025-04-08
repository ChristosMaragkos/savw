package com.savw.networking;

import com.savw.SkyAboveVoiceWithin;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/// # ShoutFailedS2CPayload
/// Unused custom payload that was meant to be sent when a player tries to use a shout
/// while on cooldown or without knowing any words.
public record ShoutFailedS2CPayload(boolean fromCooldown) implements CustomPacketPayload {

    public static final ResourceLocation SHOUT_FAILED_PAYLOAD_ID = ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "shout_failed");
    public static final Type<ShoutFailedS2CPayload> SHOUT_FAILED_PAYLOAD_TYPE = new Type<>(SHOUT_FAILED_PAYLOAD_ID);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return SHOUT_FAILED_PAYLOAD_TYPE;
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, ShoutFailedS2CPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            ShoutFailedS2CPayload::fromCooldown,
            ShoutFailedS2CPayload::new
    );

}
