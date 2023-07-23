package me.combimagnetron.lagoon.data.impl;

import me.combimagnetron.lagoon.data.DataContainer;
import me.combimagnetron.lagoon.data.DataObject;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.operation.Operation;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserDataContainer implements DataContainer {
     private final ConcurrentHashMap<Identifier, DataObject<?, ?>> localStorage = new ConcurrentHashMap<>();
    private UUID syncId;

    @Override
    public <T> Operation<DataObject<T, ?>> request(Identifier identifier) {
        return Operation.executable(() -> (DataObject<T, ?>) localStorage.get(identifier));
    }

    @Override
    public <T> Operation<DataObject<T, ?>> add(Identifier identifier, DataObject<T, ?> object) {
        return Operation.executable(() -> (DataObject<T, ?>) localStorage.put(identifier, object));
    }

    @Override
    public Operation<Integer> size() {
        return Operation.executable(localStorage::size);
    }

    public UUID syncId() {
        return syncId;
    }

    public void syncId(UUID uuid) {
        this.syncId = uuid;
    }

}
