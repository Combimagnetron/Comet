package me.combimagnetron.pilot;

import io.kubernetes.client.openapi.ApiException;
import me.combimagnetron.lagoon.communication.MessageClient;

import java.io.IOException;

public class Bootstrap {
    public static void main(String[] args) {
        MessageClient client =
                switch (args[0].toLowerCase()) {
                    default -> MessageClient.redis(args[1], Integer.parseInt(args[2]), args[3]);
                    case "pulsar" -> MessageClient.pulsar(args[1]);
                };
        try {
            new Pilot(client);
        } catch (IOException | ApiException e) {
            throw new RuntimeException(e);
        }
    }

}
