package com.savw.ui;

import io.wispforest.owo.ui.component.ButtonComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class ArrowButton extends ButtonComponent {

    protected ArrowButton(ShoutSelectionScreen parent, boolean previous) {
        super(previous ? Component.literal("←") : Component.literal("→"), buttonComponent -> {
            if (previous) {
                Minecraft.getInstance().setScreen(new ShoutSelectionScreen(parent.page() - 1));
            } else {
                Minecraft.getInstance().setScreen(new ShoutSelectionScreen(parent.page() + 1));
            }
        });
        this.tooltip(previous ? Component.literal("Previous Page") : Component.literal("Next Page"));
    }

}
