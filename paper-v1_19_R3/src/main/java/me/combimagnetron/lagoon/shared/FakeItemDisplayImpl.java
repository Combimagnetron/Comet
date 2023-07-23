package me.combimagnetron.lagoon.shared;

import com.mojang.math.Transformation;
import me.combimagnetron.lagoon.feature.entity.entity.FakeItemDisplay;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.user.User;
import me.combimagnetron.lagoon.feature.entity.math.Point;
import me.combimagnetron.lagoon.feature.entity.math.Quaternion;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemDisplayContext;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@SuppressWarnings("unchecked")
public class FakeItemDisplayImpl implements FakeItemDisplay {
    private final Collection<User<?>> observers = new LinkedHashSet<>();
    private final Display.ItemDisplay itemDisplay;

    public FakeItemDisplayImpl(EulerAngle headRot, World world) {
        this.itemDisplay = new Display.ItemDisplay(EntityType.ITEM_DISPLAY ,((CraftWorld) world).getHandle());
        this.itemDisplay.setTransformation(new Transformation(null, Quaternion.quaternion(Point.of(headRot.getX(), headRot.getY(), headRot.getZ())).toF(), null, null));
        this.headItem(new ItemStack(Material.PAPER)).async();
    }

    public FakeItemDisplayImpl(Point position, EulerAngle headRot, World world) {
        this.itemDisplay = new Display.ItemDisplay(EntityType.ITEM_DISPLAY ,((CraftWorld) world).getHandle());
        this.itemDisplay.setTransformation(new Transformation(null, Quaternion.quaternion(Point.of(headRot.getX(), headRot.getY(), headRot.getZ())).toF(), null, null));
        this.teleport(position).async();
        this.headItem(new ItemStack(Material.PAPER)).async();
    }

    @Override
    public Operation<Boolean> show(Collection<User<?>> players) {
        return Operation.executable(() -> {
            players.forEach(this::spawn);
            observers.addAll(players);
            return true;
        });
    }

    @Override
    public Operation<Boolean> hide(Collection<User<?>> players) {
        return Operation.executable(() -> {
            players.forEach(this::hide);
            observers.removeAll(players);
            return true;
        });
    }

    @Override
    public Operation<Void> headItem(org.bukkit.inventory.ItemStack stack) {
        return Operation.simple(() -> {
            itemDisplay.setItemStack(CraftItemStack.asNMSCopy(stack));
            itemDisplay.setItemTransform(ItemDisplayContext.FIXED);
            observers.forEach(this::update);
        });
    }

    @Override
    public Operation<Void> teleport(Point point) {
        return Operation.simple(() -> {
            itemDisplay.setPosRaw(point.x(), point.y(), point.z());
            itemDisplay.setRot((float) point.yaw(), (float) point.pitch());
            observers.forEach(this::update);
        });
    }

    public Operation<Void> move(Point relativeMovement) {
        return Operation.simple(() -> {
            itemDisplay.setTransformation(new Transformation(relativeMovement.vector3f(), null, null, null));
            itemDisplay.setInterpolationDuration(3);
            itemDisplay.setInterpolationDelay(-1);
            observers.forEach(this::update);
        });
    }

    public Operation<Void> move(Point relativeMovement, int lerp) {
        return Operation.simple(() -> {
            itemDisplay.setTransformation(new Transformation(relativeMovement.vector3f(), null, null, null));
            itemDisplay.setInterpolationDuration(lerp);
            itemDisplay.setInterpolationDelay(-1);
            observers.forEach(this::update);
        });
    }

    @Override
    public Operation<Collection<User<?>>> viewers() {
        return Operation.executable(() -> observers);
    }

    @Override
    public org.bukkit.entity.ArmorStand bukkitEntity() {
        return (org.bukkit.entity.ArmorStand) itemDisplay.getBukkitEntity();
    }

    @Override
    public Operation<Void> rotateHead(EulerAngle headRot) {
        return Operation.simple(() -> {
            itemDisplay.setTransformation(new Transformation(null, Quaternion.quaternion(Point.of(headRot.getX(), headRot.getY(), headRot.getZ())).toF(), null, null));
            itemDisplay.setInterpolationDuration(3);
            itemDisplay.setInterpolationDelay(-1);
            observers.forEach(this::update);
        });
    }

    @Override
    public Operation<Boolean> scale(Point point) {
        return Operation.executable(() -> {
            itemDisplay.setTransformation(new Transformation(null, null, point.vector3f(), null));
            itemDisplay.setInterpolationDuration(3);
            itemDisplay.setInterpolationDelay(-1);
            observers.forEach(this::update);
            return true;
        });
    }

    public Operation<Void> updateGroup(Collection<FakeItemDisplay> displays) {
        return Operation.simple(() -> {
            if (displays.isEmpty()) return;
            displays.add(this);
            LinkedList<Packet<ClientGamePacketListener>> packets = new LinkedList<>();
            displays.forEach(itemDisplay -> {
                FakeItemDisplayImpl fakeItemDisplay = (FakeItemDisplayImpl) itemDisplay;
                packets.add(fakeItemDisplay.updatePacket());
            });
            observers.forEach(player -> {
                ServerPlayer serverPlayer = serverPlayer(player);
                send(serverPlayer, Set.of(new ClientboundBundlePacket(packets)));
            });
        });
    }

    public void moveNoUpdate(Point relativeMovement) {
        itemDisplay.setTransformation(new Transformation(relativeMovement.vector3f(), null, null, null));
        itemDisplay.setInterpolationDuration(3);
        itemDisplay.setInterpolationDelay(-1);
    }

    public void rotateNoUpdate(EulerAngle eulerAngle) {
        itemDisplay.setTransformation(new Transformation(null, Quaternion.quaternion(Point.of(eulerAngle.getX(), eulerAngle.getY(), eulerAngle.getZ())).toF(), null, null));
        itemDisplay.setInterpolationDuration(3);
        itemDisplay.setInterpolationDelay(-1);
    }

    private void spawn(User<?> player) {
        Bukkit.getLogger().info("spawn1");
        send(serverPlayer(player), spawn());
        Bukkit.getLogger().info("spawn2");
        update(player);
        Bukkit.getLogger().info("spawn3");
    }

    private void hide(User<?> player) {
        send(serverPlayer(player), Set.of(new ClientboundRemoveEntitiesPacket(itemDisplay.getId())));
    }

    protected void update(User<?> player) {
        Bukkit.getLogger().info("spawn4");
        itemDisplay.getEntityData().refresh(serverPlayer(player));
        send(serverPlayer(player), Set.of(
                    new ClientboundTeleportEntityPacket(itemDisplay)
                )
        );
    }

    private Collection<Packet<?>> spawn() {
        return Set.of(
                new ClientboundAddEntityPacket(itemDisplay)
        );
    }

    private ServerPlayer serverPlayer(User<?> player) {
        return ((CraftPlayer) player.platformSpecificPlayer()).getHandle();
    }

    private void send(ServerPlayer serverPlayer, Collection<Packet<?>> packets) {
        packets.forEach(packet -> serverPlayer.connection.send(packet));
    }

    public ClientboundSetEntityDataPacket updatePacket() {
        List<SynchedEntityData.DataValue<?>> dataValues;
        try {
            dataValues = (List<SynchedEntityData.DataValue<?>>) itemDisplay.getEntityData().getClass().getDeclaredMethod("packAll").invoke(itemDisplay.getEntityData());
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return new ClientboundSetEntityDataPacket(itemDisplay.getId(), dataValues);
    }


}
