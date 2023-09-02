package me.combimagnetron.lagoon.resourcepack;

import me.combimagnetron.lagoon.instance.Instance;

public interface ResourcePackManager {

    ResourcePack latest();

    void update();

    void update(Instance... instances);

}
