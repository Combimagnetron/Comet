package me.combimagnetron.lagoon.communication;

import me.combimagnetron.lagoon.instance.Instance;
import me.combimagnetron.lagoon.internal.network.ByteBuffer;
import org.jetbrains.annotations.Nullable;

public interface Message {

    void write();

    int id();

    ByteBuffer buffer();

    default <T> void write(ByteBuffer.Adapter<T> adapter, T t) {
        buffer().write(adapter, t);
    }

    default <T> T read(ByteBuffer.Adapter<T> adapter) {
        return buffer().read(adapter);
    }

}
