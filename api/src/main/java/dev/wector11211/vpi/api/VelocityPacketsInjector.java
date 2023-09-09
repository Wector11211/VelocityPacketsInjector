package dev.wector11211.vpi.api;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class VelocityPacketsInjector {

    public static final String CHANNEL = "vpacket_injector";
    private static VelocityPacketsInjector instance;

    public abstract List<VelocityPacketListener<?>> getListeners(Object plugin);

    public abstract List<VelocityPacketListener<?>> getListeners();

    public abstract void registerPacketListener(Object plugin, VelocityPacketListener<?> listener);

    public abstract void unregisterPacketListener(VelocityPacketListener<?> listener);

}
