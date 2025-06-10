package me.combimagnetron.pilot;

import me.combimagnetron.comet.communication.MessageClient;
import me.combimagnetron.comet.config.Config;

import java.io.IOException;
import java.nio.file.Path;

public class Bootstrap {

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[1]);
        System.out.println(" aaa");
        MessageClient messageClient = MessageClient.redis(args[0], port, "");
        Pilot.instance = new Pilot();
    }

}
