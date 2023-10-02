package me.combimagnetron.comet.data;

import me.combimagnetron.comet.operation.Operation;

public interface DataContainer {

    <T> Operation<DataObject<T, ?>> request(Identifier identifier);

    <T> Operation<DataObject<T, ?>> add(Identifier identifier, DataObject<T, ?> object);

    Operation<Integer> size();



}
