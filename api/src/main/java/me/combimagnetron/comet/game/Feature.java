package me.combimagnetron.comet.game;

import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.file.Folder;

public interface Feature {

    Identifier identifier();

    void start(Object... arguments);

    void terminate();

    static void deploy(Feature feature) {

    }

    Folder folder();

}
