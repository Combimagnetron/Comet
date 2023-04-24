package me.combimagnetron.lagoon.data;

import me.combimagnetron.lagoon.operation.Operation;

public interface DataContainer {

    <T> Operation<DataObject<T, ?>> request(Identifier identifier);

    <T> Operation<DataObject<T, ?>> add(Identifier identifier, T t);

    Operation<Integer> size();



}
