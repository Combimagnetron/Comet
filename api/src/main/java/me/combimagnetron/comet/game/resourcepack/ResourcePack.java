package me.combimagnetron.comet.game.resourcepack;

import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.util.Version;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public interface ResourcePack {

    <T> Map<Identifier, ResourcePackFeature<T>> features();

    <T> ResourcePack add(ResourcePackFeature<T> feature);

    ResourcePack enable(Identifier identifier);

    ResourcePack disable(Identifier identifier);

    ResourcePack disableAll();

    ResourcePack enableAll();

    UUID uuid();

    Version version();

    Identifier identifier();

}
