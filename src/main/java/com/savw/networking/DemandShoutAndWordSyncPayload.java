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

public record DemandShoutAndWordSyncPayload(UUID playerUuid) implements CustomPacketPayload {

    public static final ResourceLocation DEMAND_SHOUT_AND_WORD_SYNC_ID = ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "demand_shout_and_word_sync_payload");
    public static final Type<DemandShoutAndWordSyncPayload> DEMAND_SHOUT_AND_WORD_SYNC_TYPE = new Type<>(DEMAND_SHOUT_AND_WORD_SYNC_ID);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return DEMAND_SHOUT_AND_WORD_SYNC_TYPE;
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, DemandShoutAndWordSyncPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(UUIDUtil.CODEC),
            DemandShoutAndWordSyncPayload::playerUuid,
            DemandShoutAndWordSyncPayload::new
    );

}
