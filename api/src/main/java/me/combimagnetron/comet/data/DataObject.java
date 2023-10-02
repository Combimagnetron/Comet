package me.combimagnetron.comet.data;

import me.combimagnetron.comet.data.type.DataType;

public class DataObject<T, V> {
    private final V value;
    private final DataType<T> type;

    public DataObject(DataType<T> type, V value) {
        this.value = value;
        this.type = type;
    }

    public DataType<T> type() {
        return type;
    }

    public V value() {
        return value;
    }

}
