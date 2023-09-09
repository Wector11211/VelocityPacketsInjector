package dev.wector11211.vpi.api;

import com.velocitypowered.proxy.protocol.MinecraftPacket;
import dev.wector11211.vpi.VelocityPacketsInjectorPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class VelocityPacketsInjectorImp extends VelocityPacketsInjector {

    private final VelocityPacketsInjectorPlugin plugin;
    private final Map<Object, List<VelocityPacketListener<? extends MinecraftPacket>>> listeners = new HashMap<>();

    public VelocityPacketsInjectorImp(VelocityPacketsInjectorPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<VelocityPacketListener<?>> getListeners(Object plugin) {
        return Collections.unmodifiableList(listeners.get(plugin));
    }

    @Override
    public List<VelocityPacketListener<?>> getListeners() {
        List<VelocityPacketListener<?>> list = new ArrayList<>();
        listeners.values().forEach(list::addAll);
        return list;
    }

    @Override
    public void registerPacketListener(Object plugin, VelocityPacketListener<?> listener) {
        listeners.computeIfAbsent(plugin, p -> new ArrayList<>()).add(listener);
    }

    @Override
    public void unregisterPacketListener(VelocityPacketListener<?> listener) {
        listeners.forEach((p, l) -> l.remove(listener));
    }

}
