package me.combimagnetron.generated;

import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.generated.userplanetservice.*;
import me.combimagnetron.generated.deploymenthealthservice.*;
import me.combimagnetron.generated.deploymentservice.*;
import me.combimagnetron.generated.baseservice.*;
import me.combimagnetron.generated.userservice.*;
import me.combimagnetron.generated.entityservice.*;
import java.util.HashMap;

public class SatelliteRegistry {
    public static HashMap<Integer, Class<? extends Message>> registry = new HashMap<>();
    static {
        registry.put(22, DeploymentStatusMessage.class);
        registry.put(23, StopServiceMessage.class);
        registry.put(24, CreateUserMessage.class);
        registry.put(25, GetUserMessage.class);
        registry.put(26, UpdateUserMessage.class);
        registry.put(27, DeleteUserMessage.class);
        registry.put(29, UserJoinedMessage.class);
        registry.put(130, ScaleEntityMessage.class);
        registry.put(131, UpdateEntityMessage.class);
        registry.put(132, PlayerTrackedMobsMessage.class);
        registry.put(136, UserTrackedMobsMessage.class);
        registry.put(30, UserLeftMessage.class);
        registry.put(74, SpawnEntity.class);
        registry.put(75, DespawnEntity.class);
        registry.put(76, GetEntity.class);
        registry.put(33, ReceivePacketMessage.class);
        registry.put(77, GetEntitiesInRadius.class);
        registry.put(34, SendPacketMessage.class);
        registry.put(78, GetEntitiesInBox.class);
        registry.put(79, MoveEntity.class);
        registry.put(14, LoadPlanetMessage.class);
        registry.put(15, CreatePlanetMessage.class);
        registry.put(16, DeletePlanetMessage.class);
        registry.put(17, ListPlanetsMessage.class);
        registry.put(18, SavePlanetMessage.class);
        registry.put(19, InitialInstanceHeartbeatMessage.class);
        registry.put(123, SpawnEntityMessage.class);
        registry.put(124, DespawnEntityMessage.class);
        registry.put(125, GetEntityMessage.class);
        registry.put(126, GetEntitiesInRadiusMessage.class);
        registry.put(127, GetEntitiesInBoxMessage.class);
        registry.put(128, MoveEntityMessage.class);
        registry.put(80, RotateEntity.class);
        registry.put(129, RotateEntityMessage.class);
        registry.put(81, ScaleEntity.class);
        registry.put(82, UpdateEntity.class);
        registry.put(83, PlayerTrackedMobs.class);
        registry.put(20, InstanceHeartbeatMessage.class);
        registry.put(21, DeployServiceMessage.class);
    }
}
