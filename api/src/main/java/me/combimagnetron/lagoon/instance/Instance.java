package me.combimagnetron.lagoon.instance;

import java.util.UUID;

public interface Instance {

    UUID uniqueIdentifier();

    void shutdown();

    void startup();

    void restart();

    int playerCount();

    boolean isProxy();

    Platform platform();

}
