package me.combimagnetron.lagoon.resourcepack;

import me.combimagnetron.lagoon.Comet;
import me.combimagnetron.lagoon.file.Folder;

import java.io.File;
import java.nio.file.Path;

public interface ResourcePackFeature<T> {
    static final Path ASSETS_FOLDER = Path.of(Comet.javaPlugin().getDataFolder().getPath(), "pack/assets/comet/");

    static void saveModel(String subfolder, me.combimagnetron.lagoon.file.File<?> file) {
        File file1 = ASSETS_FOLDER.resolve(subfolder).toFile();
    }

    Folder folder();

}
