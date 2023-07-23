package me.combimagnetron.lagoon.feature;

import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.file.Folder;

public interface Feature {

    Identifier identifier();

    void start(StartUpArgument... startUpArguments);

    void terminate();

    interface StartUpArgument {

    }

    static void deploy(Feature feature) {

    }

    Folder folder();

}
