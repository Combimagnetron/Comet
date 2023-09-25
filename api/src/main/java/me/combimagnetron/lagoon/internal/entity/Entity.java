package me.combimagnetron.lagoon.internal.entity;

import me.combimagnetron.lagoon.internal.entity.metadata.Metadata;
import me.combimagnetron.lagoon.internal.entity.metadata.type.*;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.internal.entity.metadata.type.Boolean;
import me.combimagnetron.lagoon.internal.entity.metadata.type.Byte;
import net.kyori.adventure.text.Component;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("unused")
public interface Entity {

    EntityId id();

    UUID uuid();

    Vector3 position();

    Data data();

    Type type();

    interface Data {

    }

    abstract class AbstractEntity implements Entity {
        private final Metadata.Template template = Metadata.BASE;
        private final EntityId id = EntityId.next();
        private final UUID uuid = UUID.randomUUID();
        private boolean onFire = false;
        private boolean crouching = false;
        private boolean sprinting = false;
        private boolean swimming = false;
        private boolean invisible = false;
        private boolean glowing = false;
        private boolean flyingElytra = false;
        private Component name = Component.empty();
        private int air = 300;
        private boolean nameVisible = true;
        private boolean silent = false;
        private boolean noGravity = false;
        private Pose pose = Pose.of(Pose.Value.STANDING);
        private int frozenPowderedSnow = 0;
        private Metadata metadata;

        public void setOnFire(boolean onFire) {
            this.onFire = onFire;
        }

        public AbstractEntity() {
            prepare();
            metadata = Metadata.merge(metadata, extend());
        }

        public void onFire(boolean onFire) {
            this.onFire = onFire;
        }

        public void crouching(boolean crouching) {
            this.crouching = crouching;
        }

        public void sprinting(boolean sprinting) {
            this.sprinting = sprinting;
        }

        public void swimming(boolean swimming) {
            this.swimming = swimming;
        }

        public void invisible(boolean invisible) {
            this.invisible = invisible;
        }

        public void glowing(boolean glowing) {
            this.glowing = glowing;
        }

        public void flyingElytra(boolean flyingElytra) {
            this.flyingElytra = flyingElytra;
        }

        public void name(Component name) {
            this.name = name;
        }

        public void air(int air) {
            this.air = air;
        }

        public void nameVisible(boolean nameVisible) {
            this.nameVisible = nameVisible;
        }

        public void silent(boolean silent) {
            this.silent = silent;
        }

        public void noGravity(boolean noGravity) {
            this.noGravity = noGravity;
        }

        public void pose(Pose pose) {
            this.pose = pose;
        }

        public void frozenPowderedSnow(int frozenPowderedSnow) {
            this.frozenPowderedSnow = frozenPowderedSnow;
        }

        public void metadata(Metadata metadata) {
            this.metadata = metadata;
        }

        public EntityId id() {
            return id;
        }

        public UUID uuid() {
            return uuid;
        }

        public boolean onFire() {
            return onFire;
        }

        public boolean crouching() {
            return crouching;
        }

        public boolean sprinting() {
            return sprinting;
        }

        public boolean swimming() {
            return swimming;
        }

        public boolean invisible() {
            return invisible;
        }

        public boolean glowing() {
            return glowing;
        }

        public boolean flyingElytra() {
            return flyingElytra;
        }

        public Component name() {
            return name;
        }

        public int air() {
            return air;
        }

        public boolean nameVisible() {
            return nameVisible;
        }

        public boolean silent() {
            return silent;
        }

        public boolean noGravity() {
            return noGravity;
        }

        public Pose pose() {
            return pose;
        }

        public int frozenPowderedSnow() {
            return frozenPowderedSnow;
        }

        public abstract Metadata extend();

        void prepare() {
            this.metadata = template.apply(
                    Byte.of((byte)0),
                    VarInt.of(air),
                    OptChat.of(name),
                    Boolean.of(nameVisible),
                    Boolean.of(silent),
                    Boolean.of(noGravity),
                    pose,
                    VarInt.of(frozenPowderedSnow)
            );
        }

    }

    record EntityId(int intValue) {
        private static final AtomicInteger INTEGER = new AtomicInteger(Integer.MIN_VALUE);

        public static EntityId next() {
            return new EntityId(INTEGER.getAndIncrement());
        }

        public static EntityId of(int id) {
            return new EntityId(id);
        }

    }

    interface Type {

        int id();

        Identifier identifier();

        Metadata metadata();

        static Type find(int id) {
            return null;
        }

        record Impl(int id, Identifier identifier, Metadata metadata) {

        }

    }

}
