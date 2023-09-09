package dev.wector11211.vpi.api;

import com.velocitypowered.proxy.protocol.MinecraftPacket;
import dev.wector11211.vpi.api.event.PacketEvent;

public interface VelocityPacketListener<T extends MinecraftPacket> {

    default void onPacketReceive(PacketEvent<T> event) {}

    default void onPacketSend(PacketEvent<T> event) {}

    Class<T> getPacketType();
}
