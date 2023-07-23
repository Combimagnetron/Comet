package me.combimagnetron.lagoon.instance;

import java.util.UUID;

public interface Instance {

    UUID uniqueIdentifier();

    void shutdown();

    void startup();

    void restart();

    int userCount();

    Platform platform();

    default boolean isProxy() {
        return platform().isProxy();
    }

}
