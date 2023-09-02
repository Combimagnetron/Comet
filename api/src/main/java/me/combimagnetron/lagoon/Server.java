package me.combimagnetron.lagoon;

import me.combimagnetron.lagoon.communication.MessageClient;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.feature.Feature;
import me.combimagnetron.lagoon.instance.Instance;
import me.combimagnetron.lagoon.user.User;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

public interface Server {

    User<?> playerByUniqueId(UUID uuid);

    Collection<User<?>> playersOnInstance(Instance instance);

    Collection<User<?>> onlinePlayers();

    //Operation<GameLevel> createGameLevel(Identifier identifier);

    MessageClient messageClient(MessageClient.Type type);

    @Nullable
    Feature feature(Identifier identifier);

    <T extends Feature> T feature(Class<T> clazz);

    Feature startFeature(Feature feature);

    Instance instance(Identifier identifier);

    Instance instance(UUID uuid);

    JavaPlugin javaPlugin();

}
