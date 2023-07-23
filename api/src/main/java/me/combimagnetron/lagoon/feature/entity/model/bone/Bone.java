package me.combimagnetron.lagoon.feature.entity.model.bone;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.combimagnetron.lagoon.feature.entity.JsonUtil;
import me.combimagnetron.lagoon.feature.entity.entity.FakeEntity;
import me.combimagnetron.lagoon.feature.entity.math.Point;
import me.combimagnetron.lagoon.feature.entity.model.Cube;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.user.User;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import java.util.*;

public class Bone {
    private final String name;
    private final UUID uuid;
    private final Point origin;
    private final EulerAngle rotation;
    private final boolean mirrorUV;
    private final boolean visibility;
    private final Cube[] cubeArray;
    private FakeEntity fakeEntity;

    public Bone(String name, UUID uuid, Point origin, EulerAngle rotation, boolean mirrorUV, boolean visibility, Cube[] cubeArray) {
        this.name = name;
        this.uuid = uuid;
        this.origin = origin;
        this.rotation = rotation;
        this.mirrorUV = mirrorUV;
        this.visibility = visibility;
        this.cubeArray = cubeArray;
    }

    public static Bone fromJson(JsonElement jsonElement, TreeMap<UUID, Cube> cubes) {
        Bukkit.getLogger().info("1");
        JsonObject innerObject = jsonElement.getAsJsonObject();
        int[] rotation = new int[]{0, 0, 0};
        if (innerObject.has("rotation")) {
            rotation = JsonUtil.readIntArray(innerObject, "rotation", 3);
        }
        Set<UUID> uuids = new HashSet<>();
        Bukkit.getLogger().info("2");
        for (JsonElement element : innerObject.getAsJsonArray("children")) {
            if (!element.isJsonPrimitive()) {
                return Bone.fromJson(element, cubes);
            }
            uuids.add(UUID.fromString(element.getAsJsonPrimitive().getAsString()));
        }
        Bukkit.getLogger().info("3");
        Cube[] children = Arrays.stream(cubes.values().toArray(new Cube[0])).filter(cube -> uuids.contains(cube.uuid())).toList().toArray(Cube[]::new);
        Bukkit.getLogger().info("4");
        return new Bone(
                innerObject.get("name").getAsString(),
                UUID.fromString(innerObject.get("uuid").getAsString()),
                Point.of(JsonUtil.readIntArray(innerObject, "origin", 3)),
                new EulerAngle(rotation[0], rotation[1], rotation[2]),
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

    public Operation<Void> headItem(ItemStack stack) {
        return fakeEntity.headItem(stack);
    }

    public Operation<Void> teleport(Point point) {
        return fakeEntity.teleport(point);
    }

    public Operation<Void> rotateHead(EulerAngle eulerAngle) {
        return fakeEntity.rotateHead(eulerAngle);
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

    public EulerAngle rotation() {
        return rotation;
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