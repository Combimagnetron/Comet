package me.combimagnetron.comet.internal.entity;

import me.combimagnetron.comet.event.EventBus;
import me.combimagnetron.comet.event.EventFilter;
import me.combimagnetron.comet.event.EventSubscription;
import me.combimagnetron.comet.event.impl.connection.UserJoinEvent;
import me.combimagnetron.comet.internal.entity.metadata.type.Vector3d;

import java.util.HashMap;
import java.util.concurrent.*;

public class EntityRenderer {
    //private final ScheduledThreadPoolExecutor threadPoolExecutor = (ScheduledThreadPoolExecutor) Executors.newVirtualThreadPerTaskExecutor();
    private final HashMap<Entity, Vector3d> entityPositionMap = new HashMap<>();

    private EntityRenderer() {
        EventBus.subscribe(UserJoinEvent.class, null, userJoinEvent -> {
            
        });
    }

}
