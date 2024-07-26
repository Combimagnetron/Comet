package me.combimagnetron.comet.data;

import me.combimagnetron.comet.data.type.DataType;
import me.combimagnetron.comet.internal.network.ByteBuffer;

public record DataObject<V>(ByteBuffer.Adapter<?> type, V value) {

    public static <V> DataObject<V> of(ByteBuffer.Adapter<?> type, V value) {
        return new DataObject<>(type, value);
    }

}
