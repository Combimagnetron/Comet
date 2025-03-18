package me.combimagnetron.comet.game.resourcepack;

import me.combimagnetron.comet.CometBase;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.util.file.Folder;

import java.nio.file.Path;

public interface ResourcePackFeature<T> {
    Path ASSETS_FOLDER = Path.of(CometBase.comet().dataFolder().toString(), "pack/assets/comet/");

    Folder folder();

    Identifier identifier();

    class Impl<T> implements ResourcePackFeature<T> {
        private final Identifier identifier;
        private final Folder folder;

        public Impl(Folder folder, Identifier identifier) {
            this.identifier = identifier;
            this.folder = folder;
        }

        @Override
        public Folder folder() {
            return folder;
        }

        @Override
        public Identifier identifier() {
            return identifier;
        }
    }

}
