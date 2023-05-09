package me.combimagnetron.lagoon.entity;

import me.combimagnetron.lagoon.feature.entity.entity.FakeArmorStand;
import me.combimagnetron.lagoon.feature.entity.math.Point;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.player.GlobalPlayer;
import net.minecraft.core.Rotations;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class FakeArmorStandImpl implements FakeArmorStand {
    private final Collection<GlobalPlayer<?>> observers = new LinkedHashSet<>();
    private final ArmorStand armorStand;

    public FakeArmorStandImpl(Point point, EulerAngle headRot, World world) {
        this.armorStand = new ArmorStand(((CraftWorld) world).getHandle(), point.x(), point.y(), point.z());
        this.armorStand.setHeadPose(Rotations.createWithoutValidityChecks((float) headRot.getX(), (float) headRot.getY(), (float) headRot.getZ()));
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
            armorStand.setItemSlot(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(stack));
            observers.forEach(this::update);
        });
    }

    @Override
    public Operation<Void> teleport(Point point) {
        return Operation.simple(() -> {
            armorStand.setPosRaw(point.x(), point.y(), point.z());
            armorStand.setRot((float) point.yaw(), (float) point.pitch());
            observers.forEach(this::update);
        });
    }

    @Override
    public Operation<Collection<GlobalPlayer<?>>> viewers() {
        return Operation.executable(() -> observers);
    }

    @Override
    public org.bukkit.entity.ArmorStand bukkitEntity() {
        return (org.bukkit.entity.ArmorStand) armorStand.getBukkitEntity();
    }

    @Override
    public Operation<Void> rotateHead(EulerAngle angle) {
        return Operation.simple(() -> {
            armorStand.setHeadPose(Rotations.createWithoutValidityChecks((float) angle.getX(), (float) angle.getY(), (float) angle.getZ()));
            observers.forEach(this::update);
        });
    }

    private void spawn(GlobalPlayer<?> player) {
        send(serverPlayer(player), spawn());
    }

    private void hide(GlobalPlayer<?> player) {
        send(serverPlayer(player), Set.of(new ClientboundRemoveEntitiesPacket(armorStand.getId())));
    }

    private void update(GlobalPlayer<?> player) {
        send(serverPlayer(player), Set.of(
                    new ClientboundSetEntityDataPacket(armorStand.getId(), armorStand.getEntityData().packDirty()),
                    new ClientboundTeleportEntityPacket(armorStand)
                )
        );
    }

    private Collection<Packet<?>> spawn() {
        return Set.of(
                new ClientboundSetEntityDataPacket(armorStand.getId(), armorStand.getEntityData().packDirty()),
                new ClientboundAddEntityPacket(armorStand)
        );
    }

    private ServerPlayer serverPlayer(GlobalPlayer<?> player) {
        return ((CraftPlayer) player.platformSpecificPlayer()).getHandle();
    }

    private void send(ServerPlayer serverPlayer, Collection<Packet<?>> packets) {
        packets.forEach(packet -> serverPlayer.connection.send(packet));
    }
}
