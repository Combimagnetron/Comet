package me.combimagnetron.comet.feature.menu;

import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.feature.Feature;
import me.combimagnetron.comet.file.Folder;

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
