package com.savw.networking;

import com.savw.SkyAboveVoiceWithin;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record RequestCooldownSyncC2SPayload(UUID playerUuid) implements CustomPacketPayload {

    public static final ResourceLocation REQUEST_COOLDOWN_SYNC_PAYLOAD_ID = ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "request_cooldown_sync_payload");
    public static final Type<RequestCooldownSyncC2SPayload> REQUEST_COOLDOWN_SYNC_PAYLOAD_TYPE = new Type<>(REQUEST_COOLDOWN_SYNC_PAYLOAD_ID);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return REQUEST_COOLDOWN_SYNC_PAYLOAD_TYPE;
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, RequestCooldownSyncC2SPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(UUIDUtil.CODEC),
            RequestCooldownSyncC2SPayload::playerUuid,
            RequestCooldownSyncC2SPayload::new
    );

}
