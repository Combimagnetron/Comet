package me.combimagnetron.comet.instance;

import java.util.Collection;
import java.util.UUID;

public interface InstanceHandler {

    Instance instance(UUID uuid);

    void register(Instance instance);

    Collection<Instance> platform(Platform platform);

}
