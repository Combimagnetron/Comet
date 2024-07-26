package me.combimagnetron.comet.feature.entity.model.bone;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.combimagnetron.comet.feature.entity.entity.FakeEntity;
import me.combimagnetron.comet.user.User;
import me.combimagnetron.comet.feature.entity.JsonUtil;
import me.combimagnetron.comet.feature.entity.math.Point;
import me.combimagnetron.comet.feature.entity.model.Cube;
import me.combimagnetron.comet.operation.Operation;

import java.util.*;

public class Bone {
    private final String name;
    private final UUID uuid;
    private final Point origin;
    private final Object rotation;
    private final boolean mirrorUV;
    private final boolean visibility;
    private final Cube[] cubeArray;
    private FakeEntity fakeEntity;

    public Bone(String name, UUID uuid, Point origin, Object rotation, boolean mirrorUV, boolean visibility, Cube[] cubeArray) {
        this.name = name;
        this.uuid = uuid;
        this.origin = origin;
        this.rotation = rotation;
        this.mirrorUV = mirrorUV;
        this.visibility = visibility;
        this.cubeArray = cubeArray;
    }

    public static Bone fromJson(JsonElement jsonElement, TreeMap<UUID, Cube> cubes) {
        JsonObject innerObject = jsonElement.getAsJsonObject();
        int[] rotation = new int[]{0, 0, 0};
        if (innerObject.has("rotation")) {
            rotation = JsonUtil.readIntArray(innerObject, "rotation", 3);
        }
        Set<UUID> uuids = new HashSet<>();
        for (JsonElement element : innerObject.getAsJsonArray("children")) {
            if (!element.isJsonPrimitive()) {
                return Bone.fromJson(element, cubes);
            }
            uuids.add(UUID.fromString(element.getAsJsonPrimitive().getAsString()));
        }
        Cube[] children = Arrays.stream(cubes.values().toArray(new Cube[0])).filter(cube -> uuids.contains(cube.uuid())).toList().toArray(Cube[]::new);
        return new Bone(
                innerObject.get("name").getAsString(),
                UUID.fromString(innerObject.get("uuid").getAsString()),
                Point.of(JsonUtil.readIntArray(innerObject, "origin", 3)),
                null,
                innerObject.get("export").getAsBoolean(),
                innerObject.get("mirror_uv").getAsBoolean(),
                children
        );
    }    public Operation<Boolean> show(Collection<User<?>> players) {
        return fakeEntity.show(players);
    }

    public Operation<Boolean> hide(Collection<User<?>> players) {
        return fakeEntity.show(players);
    }



    public String name() {
        return name;
    }

    public UUID uuid() {
        return uuid;
    }

    public Point origin() {
        return origin;
    }

    public boolean mirrorUV() {
        return mirrorUV;
    }

    public boolean visibility() {
        return visibility;
    }

    public Cube[] cubeArray() {
        return cubeArray;
    }

    public FakeEntity fakeArmorStand() {
        return fakeEntity;
    }

    public FakeEntity fakeArmorStand(FakeEntity fakeEntity) {
        return this.fakeEntity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Bone) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.uuid, that.uuid) &&
                Objects.equals(this.origin, that.origin) &&
                Objects.equals(this.rotation, that.rotation) &&
                this.mirrorUV == that.mirrorUV &&
                this.visibility == that.visibility &&
                Objects.equals(this.cubeArray, that.cubeArray);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, uuid, origin, rotation, mirrorUV, visibility, cubeArray);
    }

    @Override
    public String toString() {
        return "Bone[" +
                "name=" + name + ", " +
                "uuid=" + uuid + ", " +
                "origin=" + origin + ", " +
                "rotation=" + rotation + ", " +
                "mirrorUV=" + mirrorUV + ", " +
                "visibility=" + visibility + ", " +
                "cubeArray=" + cubeArray + ']';
    }


}