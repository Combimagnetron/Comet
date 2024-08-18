package me.combimagnetron.comet.game.entity;

import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.game.Feature;
import me.combimagnetron.comet.util.file.Folder;

public class EntityFeature implements Feature {
    private final Folder folder = Folder.empty("entity-feature");
    private final ExternalEntityFeatureComponent externalEntityFeatureComponent;


    public EntityFeature(ExternalEntityFeatureComponent externalEntityFeatureComponent) {
        this.externalEntityFeatureComponent = externalEntityFeatureComponent;
    }

    @Override
    public Identifier identifier() {
        return Identifier.of("feature", "entity");
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
