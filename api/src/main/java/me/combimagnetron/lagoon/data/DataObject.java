package me.combimagnetron.lagoon.data;

import me.combimagnetron.lagoon.data.type.DataType;
import me.combimagnetron.lagoon.event.EventBus;
import me.combimagnetron.lagoon.event.impl.spigot.SpigotEvent;

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
