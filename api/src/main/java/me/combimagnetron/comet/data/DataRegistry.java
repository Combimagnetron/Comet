package me.combimagnetron.comet.data;

import me.combimagnetron.comet.operation.Operation;

public interface DataRegistry {
    DataObject<?> add(Identifier identifier, DataObject<?> object);

    DataObject<?> get(Identifier identifier);
}
