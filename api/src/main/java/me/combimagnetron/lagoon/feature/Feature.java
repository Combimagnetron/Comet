package me.combimagnetron.lagoon.feature;

import me.combimagnetron.lagoon.data.Identifier;

public interface Feature {

    Identifier identifier();

    void start();

    void terminate();

}
