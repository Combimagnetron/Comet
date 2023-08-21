package me.combimagnetron.lagoon.communication;

import me.combimagnetron.lagoon.instance.Instance;
import me.combimagnetron.lagoon.internal.network.ByteBuffer;
import org.jetbrains.annotations.Nullable;

public abstract class Message {
    private final ByteBuffer byteBuffer;
    private final int id;
    private Instance origin;

    public abstract @Nullable Instance origin();

    public abstract void write();

    public <T> void write(ByteBuffer.Adapter<T> adapter, T t) {
        byteBuffer.write(adapter, t);
    }

    public <T> T read(ByteBuffer.Adapter<T> adapter) {
        return byteBuffer.read(adapter);
    }

    public Message(int id, Instance origin, Instance target) {
        this.byteBuffer = ByteBuffer.empty();
        this.id = id;
        this.origin = origin;
        write(ByteBuffer.Adapter.INT, id);
    }

    public Message(byte[] bytes) {
        this.byteBuffer = ByteBuffer.of(bytes);
        this.id = read(ByteBuffer.Adapter.INT);
    }

    public int id() {
        return id;
    }

    public ByteBuffer buffer() {
        return byteBuffer;
    }

}
