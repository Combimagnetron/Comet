package me.combimagnetron.generated.entityservice;

import me.combimagnetron.comet.CometBase;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.service.Deployment;
import me.combimagnetron.generated.baseservice.*;

public class EntityService implements me.combimagnetron.comet.service.Service {
    private final me.combimagnetron.comet.communication.message.MessageChannel channel = CometBase.comet().channels().serviceChannel();

    public void spawnEntity(User user, Entity entity) {
        channel.send(SpawnEntityMessage.of(user, entity));
    }

    public void despawnEntity(User user, Entity entity) {
        channel.send(DespawnEntityMessage.of(user, entity));
    }

    public Entity getEntity(java.util.UUID uuid) {
        channel.send(GetEntityMessage.of(uuid));
        returnGetEntityMessage;
    }

    public java.util.List<Entity> getEntitiesInRadius(Vector3d position, java.lang.Double radius) {
        channel.send(GetEntitiesInRadiusMessage.of(position, radius));
        returnGetEntitiesInRadiusMessage;
    }

    public java.util.List<Entity> getEntitiesInBox(Vector3d min, Vector3d max) {
        channel.send(GetEntitiesInBoxMessage.of(min, max));
        returnGetEntitiesInBoxMessage;
    }

    public void moveEntity(Entity entity, Vector3d position) {
        channel.send(MoveEntityMessage.of(entity, position));
    }

    public void rotateEntity(Entity entity, Vector3d rotation) {
        channel.send(RotateEntityMessage.of(entity, rotation));
    }

    public void scaleEntity(Entity entity, Vector3d scale) {
        channel.send(ScaleEntityMessage.of(entity, scale));
    }

    public void updateEntity(Entity entity) {
        channel.send(UpdateEntityMessage.of(entity));
    }

    public java.util.List<Entity> userTrackedMobs(User user) {
        channel.send(UserTrackedMobsMessage.of(user));
        returnUserTrackedMobsMessage;
    }

    @Override
    public Identifier identifier() {
        return null;
    }

    @Override
    public void stop() {

    }

    @Override
    public void start() {

    }

    @Override
    public void tick() {

    }

    @Override
    public Deployment deployment() {
        return null;
    }
}
