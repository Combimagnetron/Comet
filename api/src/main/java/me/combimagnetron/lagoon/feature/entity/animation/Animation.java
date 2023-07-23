package me.combimagnetron.lagoon.feature.entity.animation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.combimagnetron.lagoon.feature.entity.animation.effect.Effect;
import me.combimagnetron.lagoon.feature.entity.animation.keyframe.*;
import me.combimagnetron.lagoon.feature.entity.math.Point;
import me.combimagnetron.lagoon.feature.entity.model.ModelTemplate;
import me.combimagnetron.lagoon.feature.entity.model.Timeline;
import me.combimagnetron.lagoon.feature.entity.model.bone.Bone;
import me.combimagnetron.lagoon.operation.Operation;
import org.bukkit.Bukkit;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.*;
import java.util.stream.Collectors;

public final class Animation implements Timeline.LifeCycle {
    private final TreeMap<UUID, List<Map.Entry<Long, Point>>> positions = new TreeMap<>();
    private final TreeMap<UUID, List<Map.Entry<Long, EulerAngle>>> rotations = new TreeMap<>();
    private final TreeMap<UUID, List<Map.Entry<Long, Point>>> scales = new TreeMap<>();
    private final TreeMap<Long, List<Effect>> effects = new TreeMap<>();
    private final UUID uuid;
    private final String name;
    private final LoopMode loopMode;
    private final boolean override;
    private final long length;
    private final int snapping;
    private final boolean selected;
    private final String animTimeUpdate;
    private final String blendWeight;
    private final String startDelay;
    private final String loopDelay;
    private final Animator[] animators;
    private long tick = 0;
    private boolean stop = false;

    public Animation(UUID uuid, String name, LoopMode loopMode, boolean override, long length, int snapping, boolean selected, String animTimeUpdate, String blendWeight, String startDelay, String loopDelay, Animator[] animators) {
        this.uuid = uuid;
        this.name = name;
        this.loopMode = loopMode;
        this.override = override;
        this.length = length;
        this.snapping = snapping;
        this.selected = selected;
        this.animTimeUpdate = animTimeUpdate;
        this.blendWeight = blendWeight;
        this.startDelay = startDelay;
        this.loopDelay = loopDelay;
        this.animators = animators;
        Set<Animator> animatorSet = Arrays.stream(animators()).collect(Collectors.toCollection(LinkedHashSet::new));
        Bukkit.getLogger().info(animatorSet.size() + " animators");
        for (Animator animator : animatorSet) {
            KeyFrame[] frames = animator.keyFrames();
            Set<KeyFrame> frameSet = Arrays.stream(frames).collect(Collectors.toCollection(LinkedHashSet::new));
            for (KeyFrame keyFrame : frameSet) {
                long time = keyFrame.time();
                if (keyFrame instanceof PositionKeyFrame positionKeyFrame) {
                    positions.putIfAbsent(animator.boneIdentifier(), new LinkedList<>());
                    positions.get(animator.boneIdentifier()).add(Map.entry(time, positionKeyFrame.point()));
                } else if (keyFrame instanceof RotationKeyFrame rotationKeyFrame) {
                    rotations.putIfAbsent(animator.boneIdentifier(), new LinkedList<>());
                    rotations.get(animator.boneIdentifier()).add(Map.entry(time, rotationKeyFrame.eulerAngle()));
                } else if (keyFrame instanceof ScaleKeyFrame scaleKeyFrame) {
                    scales.putIfAbsent(animator.boneIdentifier(), new LinkedList<>());
                    scales.get(animator.boneIdentifier()).add(Map.entry(time, scaleKeyFrame.scaleAxis()));
                } else if (keyFrame instanceof ParticleKeyFrame particleKeyFrame) {
                    effects.putIfAbsent(time, new LinkedList<>());
                    effects.get(time).add(particleKeyFrame.asEffect());
                } else if (keyFrame instanceof SoundKeyFrame soundKeyFrame) {
                    effects.putIfAbsent(time, new LinkedList<>());
                    effects.get(time).add(soundKeyFrame.asEffect());
                } else if (keyFrame instanceof ScriptKeyFrame scriptKeyFrame) {
                    effects.putIfAbsent(time, new LinkedList<>());
                    effects.get(time).add(scriptKeyFrame.asEffect());
                }
            }
        }
    }

    public static Animation fromJson(JsonElement jsonElement, TreeMap<UUID, Bone> bones) {
        JsonObject innerObject = jsonElement.getAsJsonObject();
        UUID animationUuid = UUID.fromString(innerObject.get("uuid").getAsString());
        String name = innerObject.get("name").getAsString();
        LoopMode mode = LoopMode.valueOf(innerObject.get("loop").getAsString().toUpperCase());
        boolean override = innerObject.get("override").getAsBoolean();
        long length = innerObject.get("length").getAsLong();
        int snapping = innerObject.get("snapping").getAsInt();
        boolean selected = innerObject.get("selected").getAsBoolean();
        String animTimeUpdate = innerObject.get("anim_time_update").getAsString();
        String blendWeight = innerObject.get("blend_weight").getAsString();
        String startDelay = innerObject.get("start_delay").getAsString();
        String loopDelay = innerObject.get("loop_delay").getAsString();
        final Set<Animator> animators = new HashSet<>();
        innerObject.getAsJsonObject("animators").entrySet().forEach(entry -> {
            Animator animator = Animator.fromJson(entry.getKey(), entry.getValue(), bones);
            animators.add(animator);
        });
        Animator[] children = animators.toArray(new Animator[0]);
        Bukkit.getLogger().info(children.length + " animators2 size");
        return new Animation(animationUuid, name, mode, override, length, snapping, selected, animTimeUpdate, blendWeight, startDelay, loopDelay, children);
    }

    public UUID uuid() {
        return uuid;
    }

    public String name() {
        return name;
    }

    public LoopMode loopMode() {
        return loopMode;
    }

    public boolean override() {
        return override;
    }

    public long length() {
        return length;
    }

    public int snapping() {
        return snapping;
    }

    public boolean selected() {
        return selected;
    }

    public String animTimeUpdate() {
        return animTimeUpdate;
    }

    public String blendWeight() {
        return blendWeight;
    }

    public String startDelay() {
        return startDelay;
    }

    public String loopDelay() {
        return loopDelay;
    }

    public Animator[] animators() {
        return animators;
    }

    public Operation<Void> tick() {
        return Operation.simple(() -> {
            if (this.stop) return;
            tick++;
            if (tick > length) tick = 0;
        });
    }

    @Override
    public Operation<Void> stop() {
        return Operation.simple(() -> {
            this.tick = 0;
            this.stop = false;
        });
    }

    @Override
    public Operation<Void> pause() {
        return Operation.simple(() -> this.stop = true);
    }

    @Override
    public @NotNull Operation<Void> resume() {
        return Operation.simple(() -> this.stop = false);
    }

    public long currentTick() {
        return tick;
    }

    public Map<Bone, Point> currentPoints(ModelTemplate modelTemplate) {
        final LinkedHashMap<Bone, Point> points = new LinkedHashMap<>();
        modelTemplate.bones().forEach((string, bone) -> {
            Optional<Map.Entry<Long, Point>> point = this.positions.get(bone.uuid()).stream().filter(entry -> entry.getKey() == tick).findFirst();
            if (point.isEmpty()) return;
            points.put(bone, point.get().getValue());
        });
        return points;
    }

    public Map<Bone, EulerAngle> currentRotations(ModelTemplate modelTemplate) {
        final LinkedHashMap<Bone, EulerAngle> rotations = new LinkedHashMap<>();
        modelTemplate.bones().forEach((string, bone) -> {
            Optional<Map.Entry<Long, EulerAngle>> rotation = this.rotations.get(bone.uuid()).stream().filter(entry -> entry.getKey() == tick).findFirst();
            if (rotation.isEmpty()) return;
            rotations.put(bone, rotation.get().getValue());
        });
        return rotations;
    }

    @UnknownNullability
    public Point posAt(String bone, long time) {
        for (Map.Entry<Long, Point> entry : positions.get(UUID.fromString(bone))) {
            long result = positions.get(UUID.fromString(bone)).stream().mapMultiToLong((entry1, consumer) -> consumer.accept(entry1.getKey())).boxed().min(Comparator.comparingLong(l -> l - time)).orElse(0L);
            if (entry.getKey() == result) return entry.getValue();
        }
        return null;
    }

    @UnknownNullability
    public EulerAngle rotationAt(String bone, long time) {
        for (Map.Entry<Long, EulerAngle> entry : rotations.get(UUID.fromString(bone))) {
            long result = rotations.get(UUID.fromString(bone)).stream().mapMultiToLong((entry1, consumer) -> consumer.accept(entry1.getKey())).boxed().min(Comparator.comparingLong(l -> l - time)).orElse(0L);
            if (entry.getKey() == result) return entry.getValue();
        }
        return null;
    }

    @UnknownNullability
    public Point scaleAt(String bone, long time) {
        for (Map.Entry<Long, Point> entry : scales.get(UUID.fromString(bone))) {
            long result = rotations.get(UUID.fromString(bone)).stream().mapMultiToLong((entry1, consumer) -> consumer.accept(entry1.getKey())).boxed().min(Comparator.comparingLong(l -> l - time)).orElse(0L);
            if (entry.getKey() == result) return entry.getValue();
        }
        return null;
    }

    public Collection<Effect> effectAt(long time) {
        return effects.get(time);
    }

    public TreeMap<UUID, List<Map.Entry<Long, EulerAngle>>> rotations() {
        return rotations;
    }

    public TreeMap<UUID, List<Map.Entry<Long, Point>>> positions() {
        return positions;
    }

    public record Animator(UUID boneIdentifier, String name, String type, KeyFrame[] keyFrames) {

        public static KeyFrame[] keyFrames(JsonElement jsonElement, TreeMap<UUID, Bone> bones, UUID boneId) {
            JsonArray array = jsonElement.getAsJsonArray();
            Set<KeyFrame> frames = new LinkedHashSet<>();
            array.forEach(element -> {
                JsonObject innerObject = element.getAsJsonObject();
                Bone bone = bones.get(boneId);
                String interpolation = innerObject.get("interpolation").getAsString();
                JsonArray dataPoints = innerObject.getAsJsonArray("data_points");
                double x = dataPoints.get(0).getAsJsonObject().get("x").getAsDouble();
                double y = dataPoints.get(0).getAsJsonObject().get("y").getAsDouble();
                double z = dataPoints.get(0).getAsJsonObject().get("z").getAsDouble();
                KeyFrame keyFrame = switch (innerObject.get("channel").getAsString()) {
                    default -> new PositionKeyFrame(interpolation, Point.of(x, y, z), bone);
                    case "rotation" -> new RotationKeyFrame(interpolation, Point.of(x, y, z), bone);
                    case "scale" -> new ScaleKeyFrame(interpolation, Point.of(x, y, z), bone);
                    case "particle" -> new ParticleKeyFrame(interpolation, innerObject.get("data_points").getAsJsonArray().get(0).getAsString());
                    case "sound" -> new SoundKeyFrame(interpolation, innerObject.get("data_points").getAsJsonArray().get(0).getAsString());
                    case "timeline" -> new ScriptKeyFrame(interpolation, innerObject.get("data_points").getAsJsonArray().get(0).getAsString());
                };
                frames.add(keyFrame);
            });
            return frames.toArray(KeyFrame[]::new);
        }

        public static Animator fromJson(String uuid, JsonElement jsonElement, TreeMap<UUID, Bone> bones) {
            JsonObject innerObject = jsonElement.getAsJsonObject();
            UUID animatorUuid = UUID.fromString(uuid);
            String name = innerObject.get("name").getAsString();
            String type = innerObject.get("type").getAsString();
            KeyFrame[] frames = keyFrames(innerObject.get("keyframes"), bones, animatorUuid);
            return new Animator(animatorUuid, name, type, frames);
        }
    }

    enum LoopMode {
        ONCE, LOOP, HOLD
    }
}
