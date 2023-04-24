package me.combimagnetron.lagoon.data;

import me.combimagnetron.lagoon.data.type.DataType;

public abstract class DataObject<T, V> {
    private final V value;
    private final DataType<T> type;

    public DataObject(DataType<T> type, V value) {
        this.value = value;
        this.type = type;
    }

    public abstract DataType<T> type();

    public abstract V value();

}
