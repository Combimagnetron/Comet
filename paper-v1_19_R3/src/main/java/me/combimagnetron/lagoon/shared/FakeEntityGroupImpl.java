package me.combimagnetron.lagoon.shared;

import me.combimagnetron.lagoon.feature.entity.entity.FakeEntity;
import me.combimagnetron.lagoon.feature.entity.entity.FakeEntityGroup;
import me.combimagnetron.lagoon.feature.entity.math.Point;
import me.combimagnetron.lagoon.feature.entity.model.bone.Bone;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.operation.Operations;
import me.combimagnetron.lagoon.user.User;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.util.EulerAngle;

import java.util.*;

public class FakeEntityGroupImpl implements FakeEntityGroup {
    private final HashMap<Bone, FakeEntity> entityHashMap = new LinkedHashMap<>();
    private FakeEntity baseEntity;

    protected FakeEntityGroupImpl(Map<Bone, FakeEntity> entities) {
        this.entityHashMap.putAll(entities);
    }

    public static FakeEntityGroup create(Map<Bone, FakeEntity> entities) {
        return new FakeEntityGroupImpl(entities);
    }

    @Override
    public Operation<Void> show(User<?> player) {
        return Operation.simple(() -> {
            entityHashMap.forEach((bone, fakeEntity) -> Operations.async(fakeEntity.show(Set.of(player))));
            Collection<Packet<?>> packets = ridePackets();
            send(serverPlayer(player), packets);
        });
    }

    @Override
    public Operation<Void> teleport(Point point, World world) {
        return Operation.simple(() -> {
            if (baseEntity.bukkitEntity().getWorld() != world) {

            }
        });
    }

    @Override
    public Operation<Void> rotate(EulerAngle angle) {
        return Operation.simple(() -> {

        });
    }

    @Override
    public Collection<FakeEntity> members() {
        return entityHashMap.values();
    }

    @Override
    public Operation<Void> update(User<?> player) {
        return Operation.simple(() -> {
            Bukkit.getLogger().info("boo");
            ServerPlayer serverPlayer = serverPlayer(player);
            Set<Packet<ClientGamePacketListener>> packets = new HashSet<>();
            entityHashMap.forEach((bone, fakeEntity) -> {
                if (fakeEntity instanceof FakeItemDisplayImpl itemDisplay) {
                    packets.add(itemDisplay.updatePacket());
                }
            });
            send(serverPlayer, Set.of(new ClientboundBundlePacket(packets)));
        });
    }

    private Collection<Packet<?>> ridePackets() {
        Collection<Packet<?>> packets = new HashSet<>();
        FakeEntity previous = null;
        for (FakeEntity fakeEntity : entityHashMap.values()) {
            if (previous == null) {
                previous = fakeEntity;
                this.baseEntity = fakeEntity;
                continue;
            }
            packets.add(new ClientboundSetEntityLinkPacket(packetEntity(fakeEntity.bukkitEntity()), packetEntity(previous.bukkitEntity())));
        }
        return packets;
    }

    private Entity packetEntity(org.bukkit.entity.Entity entity) {
        return ((CraftEntity) entity).getHandle();
    }

    private ServerPlayer serverPlayer(User<?>  player) {
        return ((CraftPlayer) player.platformSpecificPlayer()).getHandle();
    }

    private void send(ServerPlayer serverPlayer, Collection<Packet<?>> packets) {
        packets.forEach(packet -> serverPlayer.connection.send(packet));
    }
}
