package me.combimagnetron.comet.feature.entity.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.combimagnetron.comet.feature.entity.JsonUtil;
import me.combimagnetron.comet.feature.entity.math.Point;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.UnknownNullability;

import java.util.UUID;

public record Cube(String name, UUID uuid, Point from, Point to, EulerAngle rotation, boolean boxUV, boolean locked, Point origin, Face faces) {

    @UnknownNullability
    public static Cube fromJson(JsonElement jsonElement) {
        JsonObject innerObject = jsonElement.getAsJsonObject();
        if (!innerObject.get("type").getAsString().equals("cube")){
            return null;
        }
        int[] rotation = new int[]{0, 0, 0};
        if (innerObject.has("rotation")) {
            rotation = JsonUtil.readIntArray(innerObject, "rotation", 3);
        }
        JsonObject faces = innerObject.getAsJsonObject("faces");
        UUID uuid = UUID.fromString(innerObject.get("uuid").getAsString());
        return new Cube(
                innerObject.get("name").getAsString(),
                uuid,
                Point.of(JsonUtil.readIntArray(innerObject, "from", 3)),
                Point.of(JsonUtil.readIntArray(innerObject, "to", 3)),
                new EulerAngle(rotation[0], rotation[1], rotation[2]),
                innerObject.get("box_uv").getAsBoolean(),
                innerObject.get("locked").getAsBoolean(),
                Point.of(JsonUtil.readIntArray(innerObject, "origin", 3)),
                new Face(
                        JsonUtil.readIntArray(faces.getAsJsonObject("north"), "uv", 4),
                        JsonUtil.readIntArray(faces.getAsJsonObject("east"), "uv", 4),
                        JsonUtil.readIntArray(faces.getAsJsonObject("south"), "uv", 4),
                        JsonUtil.readIntArray(faces.getAsJsonObject("west"), "uv", 4),
                        JsonUtil.readIntArray(faces.getAsJsonObject("up"), "uv", 4),
                        JsonUtil.readIntArray(faces.getAsJsonObject("down"), "uv", 4)
                )
        );
    }

    record Face(int[] north, int[] east, int[] south, int[] west, int[] up, int[] down) {
    }
}
