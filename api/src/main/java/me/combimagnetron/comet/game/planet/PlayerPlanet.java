package me.combimagnetron.comet.game.planet;

import me.combimagnetron.comet.data.DataContainer;
import me.combimagnetron.comet.internal.world.World;
import me.combimagnetron.comet.user.User;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public interface PlayerPlanet {

    User<?> owner();

    Collection<User<?>> players();

    String name();

    Map<User<?>, TrustLevel> trusted();

    World world();

    UUID uuid();

    DataContainer data();

    Privacy privacy();

    void teleport(User<?> user);

    void privacy(Privacy privacy);

    void trust(User<?> user, TrustLevel level);

    void save();

    void delete();

    Status status();

    enum Privacy {
        PRIVATE,
        PUBLIC
    }

    enum Status {
        LOADING,
        LOADED,
        UNLOADING,
        UNLOADED
    }

    enum TrustLevel {
        OWNER,
        TRUSTED,
        ASSOCIATE
    }

}
