package me.combimagnetron.pilot;

import io.avaje.inject.BeanScope;
import io.kubernetes.client.openapi.ApiException;
import me.combimagnetron.lagoon.communication.MessageClient;
import me.combimagnetron.lagoon.config.Config;

import java.io.IOException;
import java.nio.file.Path;

public class Bootstrap {

    public static void main(String[] args) throws IOException {
        MessageClient.Settings settings = Config.map(MessageClient.Settings.class, Path.of(args[0]));
        MessageClient messageClient = MessageClient.redis(settings);
        Pilot pilot = new Pilot(messageClient);
    }

}
