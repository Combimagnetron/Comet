package me.combimagnetron.comet.data.impl;

import me.combimagnetron.comet.data.DataContainer;
import me.combimagnetron.comet.data.DataObject;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.internal.network.ByteBuffer;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserDataContainer implements DataContainer {
    private final ConcurrentHashMap<Identifier, DataObject<?>> localStorage = new ConcurrentHashMap<>();
    private UUID syncId = UUID.randomUUID();

    @Override
    public <V> DataObject<V> request(Identifier identifier) {
        return (DataObject<V>) localStorage.get(identifier);
    }

    @Override
    public <V> DataObject<V> add(Identifier identifier, DataObject<V> object) {
        return (DataObject<V>) localStorage.put(identifier, object);
    }

    @Override
    public int size() {
        return localStorage.size();
    }

    public UUID syncId() {
        return syncId;
    }

    public void syncId(UUID uuid) {
        this.syncId = uuid;
    }

}
