package me.combimagnetron.lagoon.shared;

import com.mojang.math.Transformation;
import me.combimagnetron.lagoon.feature.entity.entity.FakeTextDisplay;
import me.combimagnetron.lagoon.feature.entity.math.Point;
import me.combimagnetron.lagoon.feature.entity.math.Quaternion;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.player.GlobalPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.EntityType;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class FakeTextDisplayImpl implements FakeTextDisplay {
    private final Collection<GlobalPlayer<?>> observers = new LinkedHashSet<>();
    private final Display.TextDisplay itemDisplay;

    public FakeTextDisplayImpl(EulerAngle headRot, World world) {
        this.itemDisplay = new Display.TextDisplay(EntityType.TEXT_DISPLAY ,((CraftWorld) world).getHandle());
        this.itemDisplay.setTransformation(new Transformation(null, Quaternion.quaternion(Point.of(headRot.getX(), headRot.getY(), headRot.getZ())).toF(), null, null));
    }

    @Override
    public Operation<Boolean> show(Collection<GlobalPlayer<?>> players) {
        return Operation.executable(() -> {
            players.forEach(this::spawn);
            observers.addAll(players);
            return true;
        });
    }

    @Override
    public Operation<Boolean> hide(Collection<GlobalPlayer<?>> players) {
        return Operation.executable(() -> {
            players.forEach(this::hide);
            observers.removeAll(players);
            return true;
        });
    }

    @Override
    public Operation<Void> headItem(org.bukkit.inventory.ItemStack stack) {
        return Operation.simple(() -> {
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

    @Override
    public Operation<Void> text(Component component) {
        return Operation.simple(() -> {
            itemDisplay.setText(net.minecraft.network.chat.Component.Serializer.fromJson(GsonComponentSerializer.gson().serialize(component)));
            itemDisplay.setLineWidth(Integer.MAX_VALUE);
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
    public Operation<Collection<GlobalPlayer<?>>> viewers() {
        return Operation.executable(() -> observers);
    }

    @Override
    public org.bukkit.entity.ArmorStand bukkitEntity() {
        return (org.bukkit.entity.ArmorStand) itemDisplay.getBukkitEntity();
    }

    @Override
    public Operation<Void> rotateHead(EulerAngle headRot) {
        return Operation.simple(() -> {
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

    private void spawn(GlobalPlayer<?> player) {
        send(serverPlayer(player), spawn());
        update(player);
    }

    private void hide(GlobalPlayer<?> player) {
        send(serverPlayer(player), Set.of(new ClientboundRemoveEntitiesPacket(itemDisplay.getId())));
    }

    protected void update(GlobalPlayer<?> player) {
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

    private ServerPlayer serverPlayer(GlobalPlayer<?> player) {
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
