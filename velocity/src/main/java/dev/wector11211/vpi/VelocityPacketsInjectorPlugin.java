package dev.wector11211.vpi;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.wector11211.vpi.api.VelocityPacketsInjectorImp;
import dev.wector11211.vpi.listeners.PlayerListener;
import org.slf4j.Logger;

import java.nio.file.Path;

public class VelocityPacketsInjectorPlugin {

    private final ProxyServer proxy;
    private final Logger logger;
    private final Path dataDirectory;
    private final VelocityPacketsInjectorImp packetsInjector;

    @Inject
    public VelocityPacketsInjectorPlugin(ProxyServer proxy, Logger logger, @DataDirectory Path dataDirectory) {
        this.proxy = proxy;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
        this.packetsInjector = new VelocityPacketsInjectorImp(this);
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        packetsInjector.enable();

        proxy.getEventManager().register(this, new PlayerListener(packetsInjector));
    }
}
