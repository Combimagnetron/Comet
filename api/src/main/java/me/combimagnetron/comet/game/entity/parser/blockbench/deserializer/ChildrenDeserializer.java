package me.combimagnetron.comet.game.entity.parser.blockbench.deserializer;

import com.google.gson.*;
import me.combimagnetron.comet.game.entity.parser.blockbench.BlockBenchBone;
import me.combimagnetron.comet.game.entity.parser.blockbench.BlockBenchChildren;
import me.combimagnetron.comet.game.entity.parser.blockbench.BlockBenchElementChildren;

import java.lang.reflect.Type;
import java.util.UUID;

public class ChildrenDeserializer implements JsonDeserializer<BlockBenchChildren> {

    private final GsonBuilder gson;

    public ChildrenDeserializer(GsonBuilder builder) {
        gson = builder;
    }
    @Override
    public BlockBenchChildren deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (jsonElement.isJsonObject()) {
            return gson.create().fromJson(jsonElement, BlockBenchBone.class);
        }
        return new BlockBenchElementChildren(UUID.fromString(jsonElement.getAsString()));
    }
}