package me.combimagnetron.comet.data;

import me.combimagnetron.comet.operation.Operation;

public interface DataRegistry {
    Operation<DataObject<?,?>> add(Identifier identifier, DataObject<?, ?> object);

    Operation<DataObject<?,?>> get(Identifier identifier);
}
