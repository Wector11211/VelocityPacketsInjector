package dev.wector11211.vpi.listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.proxy.connection.client.ConnectedPlayer;
import com.velocitypowered.proxy.network.Connections;
import dev.wector11211.vpi.api.VelocityPacketsInjector;
import dev.wector11211.vpi.packets.listener.PacketListener;
import io.netty.channel.Channel;

public class PlayerListener {

    private final VelocityPacketsInjector packetsInjector;

    public PlayerListener(VelocityPacketsInjector packetsInjector) {
        this.packetsInjector = packetsInjector;
    }

    @Subscribe
    public void injectPlayer(LoginEvent event) {
        ConnectedPlayer connectedPlayer = (ConnectedPlayer) event.getPlayer();
        connectedPlayer.getConnection()
                .getChannel()
                .pipeline()
                .addBefore(Connections.HANDLER, VelocityPacketsInjector.CHANNEL, new PacketListener(packetsInjector, connectedPlayer));
    }

    @Subscribe
    public void uninjectPlayer(DisconnectEvent event) {
        ConnectedPlayer connectedPlayer = (ConnectedPlayer) event.getPlayer();
        Channel channel = connectedPlayer.getConnection().getChannel();
        channel.eventLoop().submit(() -> {
            channel.pipeline().remove(VelocityPacketsInjector.CHANNEL);
        });
    }
}
