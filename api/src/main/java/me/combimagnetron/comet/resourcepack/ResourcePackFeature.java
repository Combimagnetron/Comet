package me.combimagnetron.comet.resourcepack;

import me.combimagnetron.comet.file.Folder;
import me.combimagnetron.comet.Comet;

import java.nio.file.Path;

public interface ResourcePackFeature<T> {
    Path ASSETS_FOLDER = Path.of(Comet.javaPlugin().getDataFolder().getPath(), "pack/assets/comet/");

    Folder folder();

}
