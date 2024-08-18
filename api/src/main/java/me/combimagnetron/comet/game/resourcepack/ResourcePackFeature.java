package me.combimagnetron.comet.game.resourcepack;

import me.combimagnetron.comet.CometBase;
import me.combimagnetron.comet.util.file.Folder;

import java.nio.file.Path;

public interface ResourcePackFeature<T> {
    Path ASSETS_FOLDER = Path.of(CometBase.comet().dataFolder().toString(), "pack/assets/comet/");

    Folder folder();

}
