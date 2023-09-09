package dev.wector11211.vpi.packets.listener;

import com.velocitypowered.proxy.connection.client.ConnectedPlayer;
import com.velocitypowered.proxy.protocol.MinecraftPacket;
import dev.wector11211.vpi.api.VelocityPacketListener;
import dev.wector11211.vpi.api.VelocityPacketsInjector;
import dev.wector11211.vpi.api.event.PacketEvent;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.util.function.Consumer;
import java.util.function.Function;

public class PacketListener extends ChannelDuplexHandler {

    private final ConnectedPlayer connectedPlayer;
    private final VelocityPacketsInjector packetsInjector;

    public PacketListener(VelocityPacketsInjector packetsInjector, ConnectedPlayer connectedPlayer) {
        this.packetsInjector = packetsInjector;
        this.connectedPlayer = connectedPlayer;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof MinecraftPacket packet) {
            if (processPacket(packet, listener -> listener::onPacketReceive)) return;
        }
        super.channelRead(ctx, msg);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof MinecraftPacket packet) {
            if (processPacket(packet, listener -> listener::onPacketSend)) return;
        }
        super.write(ctx, msg, promise);
    }

    public boolean processPacket(MinecraftPacket packet, Function<VelocityPacketListener, Consumer<PacketEvent>> procesingFunction) {
        PacketEvent event = new PacketEvent<>(packet, connectedPlayer);
        for (VelocityPacketListener<?> listener : packetsInjector.getListeners()) {
            if (!listener.getPacketType().isAssignableFrom(packet.getClass())) continue;
            procesingFunction.apply(listener).accept(event);
        }
        return event.isCancelled();
    }

}
