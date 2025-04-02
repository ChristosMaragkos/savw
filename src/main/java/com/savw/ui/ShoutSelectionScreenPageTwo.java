package com.savw.ui;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.GridLayout;
import io.wispforest.owo.ui.core.Insets;
import io.wispforest.owo.ui.core.OwoUIAdapter;
import io.wispforest.owo.ui.core.Surface;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class ShoutSelectionScreenPageTwo extends BaseOwoScreen<GridLayout> {
    @Override
    protected @NotNull OwoUIAdapter<GridLayout> createAdapter() {
        return OwoUIAdapter.create(this, ((sizing, sizing2) ->
                Containers.grid(sizing, sizing2, 3, 3)));
    }

    @Override
    protected void build(GridLayout rootComponent) {
        rootComponent
                .surface(Surface.VANILLA_TRANSLUCENT)
                .padding(Insets.top(32).withLeft(64));

        rootComponent.child(Components.button(Component.literal("x"), xButton -> Minecraft.getInstance().setScreen(null)).tooltip(Component.literal("Close")), 0, 2);

        rootComponent.child(Components.button(Component.literal("←"), buttonComponent -> Minecraft.getInstance().setScreen(new ShoutSelectionScreenPageOne())).tooltip(Component.literal("Previous Page")), 1, 0);

        rootComponent.child(Components.label(Component.literal("Look out for more Shouts\nin future updates!")), 0, 1);

    }
}
