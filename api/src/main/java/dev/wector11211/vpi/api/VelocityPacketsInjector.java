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


    public static @NotNull VelocityPacketsInjector get() {
        if (instance == null) {
            throw new IllegalStateException("""
                    The VelocityPacketsInjector isn't loaded yet!
                    This could be because:
                      1) VelocityPacketsInjector plugin's api was included as 'implementation' instead of 'compileOnly'
                      2) VelocityPacketsInjector plugin is not present or it failed to load
                      3) Your plugin does not declare a dependency on 'velocitypacketsinjector'
                      4) Your plugin is retrieving the API before the plugin 'enable' phase
                         (call the #get() method inside ProxyInitializeEvent, not any of constructors!)
                    """);
        }
        return instance;
    }

    static void register(VelocityPacketsInjector packetsInjector) {
        instance = packetsInjector;
    }

}
