package me.combimagnetron.lagoon.resourcepack;

import me.combimagnetron.lagoon.Comet;
import me.combimagnetron.lagoon.file.Folder;

import java.io.File;
import java.nio.file.Path;

public interface ResourcePackFeature<T> {
    Path ASSETS_FOLDER = Path.of(Comet.javaPlugin().getDataFolder().getPath(), "pack/assets/comet/");

    Folder folder();

}
