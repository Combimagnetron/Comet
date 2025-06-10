package me.combimagnetron.comet.game.tempworld.level;

import com.google.gson.JsonObject;
import me.combimagnetron.comet.data.Identifier;

import java.util.UUID;

public abstract class GameLevel {
    private final UUID uuid = UUID.randomUUID();
    private final Identifier identifier;
    private GameLevelData data;
    private GameLevelSettings settings;

    public GameLevel(Identifier identifier, GameLevelData data) {
        this.identifier = identifier;
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
