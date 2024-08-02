package me.combimagnetron.comet;

import me.combimagnetron.comet.communication.MessageClient;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.game.Feature;
import me.combimagnetron.comet.instance.Instance;
import me.combimagnetron.comet.user.User;
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


}
