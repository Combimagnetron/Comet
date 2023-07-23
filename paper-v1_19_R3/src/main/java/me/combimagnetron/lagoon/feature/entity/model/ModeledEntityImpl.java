package me.combimagnetron.lagoon.feature.entity.model;

import me.combimagnetron.lagoon.Comet;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.feature.entity.Duration;
import me.combimagnetron.lagoon.feature.entity.animation.Animation;
import me.combimagnetron.lagoon.feature.entity.animation.keyframe.KeyFrame;
import me.combimagnetron.lagoon.feature.entity.animation.keyframe.PositionKeyFrame;
import me.combimagnetron.lagoon.feature.entity.animation.keyframe.RotationKeyFrame;
import me.combimagnetron.lagoon.feature.entity.animation.keyframe.ScaleKeyFrame;
import me.combimagnetron.lagoon.feature.entity.entity.FakeEntity;
import me.combimagnetron.lagoon.feature.entity.entity.FakeEntityGroup;
import me.combimagnetron.lagoon.feature.entity.entity.FakeItemDisplay;
import me.combimagnetron.lagoon.feature.entity.math.Point;
import me.combimagnetron.lagoon.feature.entity.model.bone.Bone;
import me.combimagnetron.lagoon.feature.entity.model.bone.HeadBone;
import me.combimagnetron.lagoon.feature.entity.model.bone.MountBone;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.operation.Operations;
import me.combimagnetron.lagoon.shared.FakeEntityGroupImpl;
import me.combimagnetron.lagoon.shared.FakeItemDisplayImpl;
import me.combimagnetron.lagoon.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static me.combimagnetron.lagoon.operation.Operations.async;


public class ModeledEntityImpl implements ModeledEntity {
    private final Set<User<?>> observers = new HashSet<>();
    private final Map<Bone, FakeEntity> bones = new LinkedHashMap<>();
    private final TimelineImpl timeline = new TimelineImpl(this);
    private final FakeEntityGroup group;
    private final Identifier identifier;
    private final ModelTemplate modelTemplate;
    private final Entity baseEntity;
    private Point position;
    private World world;

    public ModeledEntityImpl(ModelTemplate modelTemplate, Entity baseEntity) {
        this.identifier = modelTemplate.identifier();
        this.modelTemplate = modelTemplate;
        this.baseEntity = baseEntity;
        this.world = baseEntity.getWorld();
        this.position = Point.of(baseEntity.getLocation().x(), baseEntity.getLocation().y(), baseEntity.getLocation().z());
        Bukkit.getOnlinePlayers().forEach(player -> {
            observers.add(Comet.userByUniqueId(player.getUniqueId()));
        });
        constructBones();
        this.group = FakeEntityGroupImpl.create(bones);
        Bukkit.getLogger().info("baa");
        timeline.animation(modelTemplate.animations().values().stream().findAny().orElseThrow());
        Operations.asyncRepeating(this.tick(), Duration.of(50L, TimeUnit.MILLISECONDS));
    }

    private void constructBones() {
        Bukkit.getLogger().info("bones: " + modelTemplate.bones().size());
        Collection<Bone> boneSet = modelTemplate.bones().values();
        boneSet.forEach(bone -> {
            FakeEntity itemDisplay = new FakeItemDisplayImpl(position, bone.rotation(), world); //new FakeItemDisplayImpl(bone.rotation(), world);
            itemDisplay.show(observers).async();
            bones.put(bone, itemDisplay);
        });
        baseEntity.getPersistentDataContainer().set(new NamespacedKey("comet", "model"), PersistentDataType.STRING, identifier.string());
    }

    @Override
    public Identifier identifier() {
        return identifier;
    }

    @Override
    public Timeline timeline() {
        return timeline;
    }

    @Override
    public Collection<Bone> bones() {
        return modelTemplate.bones().values();
    }

    @Override
    public Bone bone(String name) {
        return modelTemplate.bones().values().stream().filter(bone -> bone.name().equals(name)).findFirst().orElseThrow();
    }

    @Override
    public Animation fallback() {
        return modelTemplate.animations().values().stream().findAny().orElseThrow();
    }

    @Override
    public ModelTemplate template() {
        return modelTemplate;
    }

    @Override
    public Operation<Void> teleport(Point point) {
        return null;
    }

    @Override
    public Operation<Void> rotate(String name, EulerAngle angle) {
        return bone(name).rotateHead(angle);
    }

    @Override
    public @Nullable Operation<Void> mountEntity(Entity entity) {
        if (!hasMountBone()) return null;
        return getMountBone().mount(entity);
    }

    @Override
    public @Nullable Operation<Void> dismountEntity(Entity entity) {
        if (!hasMountBone()) return null;
        return getMountBone().dismount(entity);
    }

    @Override
    public Operation<Void> headRotation(EulerAngle angle) {
        return getHeadBone().rotateHead(angle);
    }

    @Override
    public Operation<Point> position() {
        return Operation.executable(() -> position);
    }

    @Override
    public Operation<Boolean> tick() {
        return Operation.executable(() -> {
            Animation animation = ((TimelineImpl) timeline).currentlyPlaying() == null ? ((TimelineImpl) timeline).currentlyPlaying() : fallback();
            if (animation == null) return false;
            async(animation.tick());
            bones.forEach((bone, fakeEntity) -> {
                Point updated = animation.posAt(bone.name(), animation.currentTick());
                EulerAngle rot = animation.rotationAt(bone.name(), animation.currentTick());
                if (fakeEntity instanceof FakeItemDisplayImpl itemDisplay) {
                    itemDisplay.moveNoUpdate(updated.sub(position.x(), position.y(), position.z()));
                    itemDisplay.rotateNoUpdate(rot);
                }
            });
            observers.forEach(observer -> async(group.update(observer)));
            return true;
        });
    }

    private void handleKeyFrame(KeyFrame keyFrame, long time) {
        if (keyFrame.time() != time) return;
        FakeEntity fakeEntity = bones.get(keyFrame.bone());
        if (keyFrame instanceof PositionKeyFrame positionKeyFrame) {
            async(fakeEntity.teleport(positionKeyFrame.point()));
        } else if (keyFrame instanceof RotationKeyFrame rotationKeyFrame) {
            async(fakeEntity.rotateHead(rotationKeyFrame.eulerAngle()));
        } else if (keyFrame instanceof ScaleKeyFrame scaleKeyFrame && fakeEntity instanceof FakeItemDisplay fakeItemDisplay) {
            fakeItemDisplay.scale(scaleKeyFrame.scaleAxis());
        }
    }

    private boolean hasMountBone() {
        return modelTemplate.bones().values().stream().anyMatch(bone -> bone instanceof MountBone);
    }

    private MountBone getMountBone() {
        return (MountBone) modelTemplate.bones().values().stream().filter(bone -> bone instanceof MountBone).findFirst().orElse(null);
    }

    private HeadBone getHeadBone() {
        return (HeadBone) modelTemplate.bones().values().stream().filter(bone -> bone instanceof HeadBone).findAny().orElse(null);
    }

    private Point calculateOffset(Bone bone, long tick) {
        Location location = baseEntity.getLocation();
        Point base = Point.of(location.getX(), location.getY(), location.getZ());
        Point bonePoint = async(timeline.bonePosAtTime(bone, tick));
        return base.sub(bonePoint.x(), bonePoint.y(), bonePoint.z());
    }
}