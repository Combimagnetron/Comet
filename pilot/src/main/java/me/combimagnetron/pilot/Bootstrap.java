package me.combimagnetron.pilot;

import me.combimagnetron.lagoon.communication.MessageClient;
import me.combimagnetron.lagoon.config.Config;

import java.io.IOException;
import java.nio.file.Path;

public class Bootstrap {

    public static void main(String[] args) throws IOException {
        MessageClient.Settings settings = Config.map(MessageClient.Settings.class, Path.of(args[0]));
        MessageClient messageClient = MessageClient.redis(settings);
        Pilot.instance = new Pilot(messageClient);
    }

}
