package me.combimagnetron.lagoon;


import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import org.slf4j.Logger;

import java.util.UUID;

@Plugin(id = "comet", name = "Comet")
public class LagoonProxyPlugin {

    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public LagoonProxyPlugin(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
        RegisteredServer serverStream = server.getAllServers().stream().mapToInt(registeredServer -> registeredServer.getPlayersConnected().size()).min().stream().mapToObj(i -> server.getAllServers().stream().filter(registeredServer -> registeredServer.getPlayersConnected().size() == i)).findAny().orElseThrow().findAny().orElseThrow();
        server.getPlayer(UUID.randomUUID()).get().createConnectionRequest(serverStream).fireAndForget();
    }

}
