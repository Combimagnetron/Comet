package me.combimagnetron.comet.feature.entity.parser.blockbench;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import me.combimagnetron.comet.feature.entity.animation.Animation;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.feature.entity.math.Point;
import me.combimagnetron.comet.feature.entity.model.Cube;
import me.combimagnetron.comet.feature.entity.model.ModelTemplate;
import me.combimagnetron.comet.feature.entity.model.bone.Bone;
import me.combimagnetron.comet.operation.Operation;
import me.combimagnetron.comet.feature.entity.parser.ModelParser;
import me.combimagnetron.comet.feature.entity.JsonUtil;
import org.bukkit.Bukkit;
import org.bukkit.util.EulerAngle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class BlockBenchParser implements ModelParser {
    private final static Gson GSON = new Gson();
    private final JsonObject jsonObject;
    private final TreeMap<UUID, Animation> animations = new TreeMap<>();
    private final TreeMap<UUID, Bone> bones = new TreeMap<>();
    private final TreeMap<UUID, Cube> cubes = new TreeMap<>();

    @Override
    public Operation<Collection<Bone>> bones() {
        return Operation.executable(bones::values);
    }

    @Override
    public Operation<Collection<Animation>> animations() {
        return Operation.executable(animations::values);
    }

    public TreeMap<UUID, Animation> animation() {
        return animations;
    }

    public TreeMap<UUID, Bone> bone() {
        return bones;
    }
    public Operation<ModelTemplate> template(Identifier identifier) {
        return Operation.executable(() -> {
            ModelTemplate modelTemplate = new ModelTemplate(identifier);
            modelTemplate.animations().putAll(animations);
            modelTemplate.bones().putAll(bones);
            return modelTemplate;
        });
    }

    protected BlockBenchParser(File file) throws FileNotFoundException {
        JsonReader jsonReader = GSON.newJsonReader(new FileReader(file));
        this.jsonObject = JsonParser.parseReader(jsonReader).getAsJsonObject();
        readCubes();
        readBones();
        readAnimations();
        Bukkit.getLogger().info(animations.size() + " " + bones.size() + " " + cubes.size());
    }

    public static BlockBenchParser read(File file) {
        try {
            return new BlockBenchParser(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void readCubes() {
        TreeMap<UUID, Cube> cubes = new TreeMap<>();
        this.jsonObject.get("elements").getAsJsonArray().forEach(element -> {
            Cube cube = Cube.fromJson(element);
            cubes.put(cube.uuid(), cube);
        });
        this.cubes.putAll(cubes);
    }

    private void readBones() {
        TreeMap<UUID, Bone> bones = new TreeMap<>();
        this.jsonObject.get("outliner").getAsJsonArray().forEach(element -> {
            JsonObject innerObject = element.getAsJsonObject();
            String name = innerObject.get("name").getAsString();
            int[] rotation = new int[]{0, 0, 0};
            if (innerObject.has("rotation")) {
                rotation = JsonUtil.readIntArray(innerObject, "rotation", 3);
            }
            Set<UUID> uuids = new HashSet<>();
            for (JsonElement element1 : innerObject.getAsJsonArray("children")) {
                Bukkit.getLogger().info(element1.toString());
                if (!element1.isJsonPrimitive()) {
                    Bone bone1 = Bone.fromJson(element1, cubes);
                    bones.put(bone1.uuid(), bone1);
                    Bukkit.getLogger().info(bone1.name());
                    continue;
                }
                uuids.add(UUID.fromString(element1.getAsJsonPrimitive().getAsString()));
            }
            Cube[] children = Arrays.stream(cubes.values().toArray(new Cube[0])).filter(cube -> uuids.contains(cube.uuid())).toList().toArray(Cube[]::new);
            Bone bone = new Bone(
                    name,
                    UUID.fromString(innerObject.get("uuid").getAsString()),
                    Point.of(JsonUtil.readIntArray(innerObject, "origin", 3)),
                    new EulerAngle(rotation[0], rotation[1], rotation[2]),
                    innerObject.get("export").getAsBoolean(),
                    innerObject.get("mirror_uv").getAsBoolean(),
                    children
            );
            Bukkit.getLogger().info(bone.name());
            bones.put(bone.uuid(), bone);
        });
        Bukkit.getLogger().info(bones.size() + "");
        this.bones.putAll(bones);
    }

    private void readAnimations() {
        TreeMap<UUID, Animation> animations = new TreeMap<>();
        this.jsonObject.get("animations").getAsJsonArray().forEach(element -> {
            Animation animation = Animation.fromJson(element, bones);
            Bukkit.getLogger().info(animation.name());
            animations.put(animation.uuid(), animation);
        });
        animations.values().forEach(animation -> {
            Bukkit.getLogger().info(animation.name());
        });
        this.animations.putAll(animations);
    }

    private static int[] readIntArray(JsonObject object, String name, int size) {
        return JsonUtil.readIntArray(object, name, size);
    }




}
