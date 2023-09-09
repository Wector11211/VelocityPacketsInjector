package dev.wector11211.vpi.api.event;

import com.velocitypowered.proxy.connection.client.ConnectedPlayer;
import com.velocitypowered.proxy.protocol.MinecraftPacket;

public class PacketEvent<T extends MinecraftPacket> {
    private final ConnectedPlayer player;
    private final T packet;
    boolean isCancelled = false;

    public PacketEvent(T packet, ConnectedPlayer player) {
        this.packet = packet;
        this.player = player;
    }

    public ConnectedPlayer getPlayer() {
        return player;
    }

    public T getPacket() {
        return packet;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public boolean setCancelled(boolean value) {
        return isCancelled = value;
    }
}
