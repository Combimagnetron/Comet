package me.combimagnetron.lagoon.feature.menu;

import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.feature.Feature;
import me.combimagnetron.lagoon.file.Folder;

public class MenuFeature implements Feature {
    private final Folder folder = Folder.empty("menu-feature");

    @Override
    public Identifier identifier() {
        return Identifier.of("feature", "menu");
    }

    @Override
    public void start(Object... arguments) {

    }

    @Override
    public void terminate() {

    }

    @Override
    public Folder folder() {
        return folder;
    }
}
