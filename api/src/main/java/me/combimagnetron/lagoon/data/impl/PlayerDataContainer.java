package me.combimagnetron.lagoon.data.impl;

import me.combimagnetron.lagoon.data.DataContainer;
import me.combimagnetron.lagoon.data.DataObject;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.operation.Operation;

public class PlayerDataContainer implements DataContainer {
    @Override
    public <T> Operation<DataObject<T, ?>> request(Identifier identifier) {
        return null;
    }

    @Override
    public <T> Operation<DataObject<T, ?>> add(Identifier identifier, T t) {
        return null;
    }

    @Override
    public Operation<Integer> size() {
        return null;
    }
}
