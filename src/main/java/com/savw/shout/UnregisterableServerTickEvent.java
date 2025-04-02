package com.savw.shout;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * This class manages server tick events that can be registered and unregistered, to prevent memory leaks.
 */
public final class UnregisterableServerTickEvent {

    /**
     * A set of callbacks to be executed at the end of each server tick.
     */
    @SuppressWarnings("FieldMayBeFinal")
    private static Set<Consumer<MinecraftServer>> callbacks = new HashSet<>();

    static {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (Consumer<MinecraftServer> callback : callbacks) {
                callback.accept(server);
            }
        });
    }

    /**
     * Registers a callback to be executed at the end of each server tick.
     *
     * @param callback the callback to register
     */
    public static void register(Consumer<MinecraftServer> callback) {
        callbacks.add(callback);
    }

    /**
     * Unregisters a previously registered callback.
     *
     * @param callback the callback to unregister
     */
    public static void unregister(Consumer<MinecraftServer> callback) {
        callbacks.remove(callback);
    }

}
