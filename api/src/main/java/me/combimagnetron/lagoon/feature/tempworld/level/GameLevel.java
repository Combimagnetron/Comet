package me.combimagnetron.lagoon.feature.tempworld.level;

import com.google.gson.JsonObject;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.feature.tempworld.level.GameLevelData;

import java.util.UUID;

public abstract class GameLevel {
    private final UUID uuid = UUID.randomUUID();
    private final Identifier identifier;
    private final GameLevelData data;
    private GameLevelSettings settings;

    public GameLevel(Identifier identifier) {
        this.identifier = identifier;
        this.data = null;
    }

    public Identifier identifier() {
        return identifier;
    }

    public UUID uniqueIdentifier() {
        return uuid;
    }

    public GameLevelData data() {
        return data;
    }

    public GameLevelSettings settings() {
        return settings;
    }

    public JsonObject serialize() {
        JsonObject object = new JsonObject();
        object.addProperty("identifier", identifier.namespace() + ":" + identifier.key());
        object.addProperty("uuid", uuid.toString());


        return object;
    }

}
