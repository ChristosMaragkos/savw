package com.savw.networking;

import com.savw.SkyAboveVoiceWithin;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/// # SyncCooldownS2CPayload
/// This record represents a payload for synchronizing the cooldown of a shout between the server and client.
/// It is sent on-demand when
public record SyncCooldownS2CPayload(Integer shoutCooldown) implements CustomPacketPayload {

    public static final ResourceLocation COOLDOWN_SYNC_PAYLOAD_ID = ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "cooldown_sync_payload");
    public static final Type<SyncCooldownS2CPayload> COOLDOWN_SYNC_PAYLOAD_TYPE = new Type<>(COOLDOWN_SYNC_PAYLOAD_ID);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return COOLDOWN_SYNC_PAYLOAD_TYPE;
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncCooldownS2CPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            SyncCooldownS2CPayload::shoutCooldown,
            SyncCooldownS2CPayload::new
    );

}
