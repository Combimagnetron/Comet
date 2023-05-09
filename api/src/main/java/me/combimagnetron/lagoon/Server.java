package me.combimagnetron.lagoon;

import me.combimagnetron.lagoon.communication.MessageClient;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.feature.Feature;
import me.combimagnetron.lagoon.game.level.GameLevel;
import me.combimagnetron.lagoon.instance.Instance;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.player.GlobalPlayer;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

public interface Server {

    GlobalPlayer<?> playerByUniqueId(UUID uuid);

    Collection<GlobalPlayer<?>> playersOnInstance(Instance instance);

    Collection<GlobalPlayer<?>> onlinePlayers();

    Operation<GameLevel> createGameLevel(Identifier identifier);

    MessageClient messageClient(MessageClient.Type type);

    @Nullable
    Feature feature(Identifier identifier);

    Feature startFeature(Feature feature);

}
