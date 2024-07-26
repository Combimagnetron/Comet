package me.combimagnetron.comet.data;

import me.combimagnetron.comet.internal.network.ByteBuffer;

public interface DataContainer {

    <V> DataObject<V> request(Identifier identifier);

    <V> DataObject<V> add(Identifier identifier, DataObject<V> object);

    int size();



}
