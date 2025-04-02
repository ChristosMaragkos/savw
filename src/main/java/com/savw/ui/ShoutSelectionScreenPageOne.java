package com.savw.ui;

import com.savw.SkyAboveVoiceWithinClient;
import com.savw.networking.ChangeShoutPayload;
import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.GridLayout;
import io.wispforest.owo.ui.core.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;

import static com.savw.shout.Shouts.ALL_SHOUTS;

public class ShoutSelectionScreenPageOne extends BaseOwoScreen<GridLayout> {

    //ResourceLocation placeholderIcon = ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/placeholder.png");

    @Override
    protected @NotNull OwoUIAdapter<GridLayout> createAdapter() {
        return OwoUIAdapter.create(this, ((sizing, sizing2) ->
                Containers.grid(sizing, sizing2, 3, 4)));
    }

    @Override
    protected void build(GridLayout gridLayout) {
        gridLayout
                .surface(Surface.VANILLA_TRANSLUCENT)
                .padding(Insets.top(32).withLeft(64));

        gridLayout.child(Components.button(Component.literal("x").withStyle(ChatFormatting.BOLD), xButton -> Minecraft.getInstance().setScreen(null)).tooltip(Component.literal("Close")), 0, 3);

        gridLayout.child(Components.button(Component.literal("→"), buttonComponent -> Minecraft.getInstance().setScreen(new ShoutSelectionScreenPageTwo())).tooltip(Component.literal("Next Page")), 1, 3);

        AtomicInteger row = new AtomicInteger();
        AtomicInteger col = new AtomicInteger();

        ALL_SHOUTS.forEach(shout -> {
            gridLayout.child(Components.button(Component.literal(shout.getName()), shoutSelectButton -> {
                SkyAboveVoiceWithinClient.clientPlayerData.currentShout = shout;
                ClientPlayNetworking.send(new ChangeShoutPayload(shout));
                Minecraft.getInstance().setScreen(null);
            }).tooltip(Component.literal(shout.getName())), row.get(), col.get());
            row.getAndIncrement();
            if (row.get() > 2) {
                row.set(0);
                col.getAndIncrement();
            }
        });
    }
}
