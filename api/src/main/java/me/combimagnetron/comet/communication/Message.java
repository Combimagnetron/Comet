package me.combimagnetron.comet.communication;

import me.combimagnetron.comet.internal.network.ByteBuffer;

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
