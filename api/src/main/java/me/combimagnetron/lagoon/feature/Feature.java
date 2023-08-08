package me.combimagnetron.lagoon.feature;

import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.file.Folder;

public interface Feature {

    Identifier identifier();

    void start(Object... arguments);

    void terminate();

    static void deploy(Feature feature) {

    }

    Folder folder();

}
