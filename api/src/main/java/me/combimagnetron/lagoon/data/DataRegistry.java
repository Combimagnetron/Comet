package me.combimagnetron.lagoon.data;

import me.combimagnetron.lagoon.operation.Operation;

public interface DataRegistry {
    Operation<DataObject<?,?>> add(Identifier identifier, DataObject<?, ?> object);

    Operation<DataObject<?,?>> get(Identifier identifier);
}
