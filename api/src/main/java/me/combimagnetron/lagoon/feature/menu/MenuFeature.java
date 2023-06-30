package me.combimagnetron.lagoon.feature.menu;

import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.feature.Feature;

public class MenuFeature implements Feature {

    @Override
    public Identifier identifier() {
        return Identifier.of("feature", "menu");
    }

    @Override
    public void start(StartUpArgument... startUpArguments) {

    }

    @Override
    public void terminate() {

    }
}
