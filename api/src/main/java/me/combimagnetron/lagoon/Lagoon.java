package me.combimagnetron.lagoon;

import me.combimagnetron.lagoon.communication.MessageClient;
import me.combimagnetron.lagoon.player.GlobalPlayer;

import java.util.UUID;

public class Lagoon {
    private static Server server;

    public static void server(Server server) {
        if (Lagoon.server != null) {
            throw new RuntimeException("Can't initialize the Server singleton twice!");
        }
        Lagoon.server = server;
    }

    public static GlobalPlayer<?> playerByUniqueId(UUID uuid) {
        return server.playerByUniqueId(uuid);
    }

    public static MessageClient messageClient(MessageClient.Type type) {
        return server.messageClient(type);
    }




}
