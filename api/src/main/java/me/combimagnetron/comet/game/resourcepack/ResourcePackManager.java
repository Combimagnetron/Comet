package me.combimagnetron.comet.game.resourcepack;

import me.combimagnetron.comet.instance.Instance;

public interface ResourcePackManager {

    ResourcePack latest();

    void update();

    void update(Instance... instances);

}
