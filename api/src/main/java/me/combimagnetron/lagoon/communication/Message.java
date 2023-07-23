package me.combimagnetron.lagoon.communication;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.combimagnetron.lagoon.communication.serializer.ByteBuffer;
import me.combimagnetron.lagoon.instance.Instance;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.UUID;

public abstract class Message extends ByteBuffer {
    private final int id;
    private Instance origin;

    public abstract @Nullable Instance origin();

    public abstract void write();

    public Message(int id, Instance origin, Instance target) {
        this.id = id;
        this.origin = origin;
        writeInt(id);
    }

    public Message(byte[] bytes) {
        read(bytes);
        this.id = readInt();
    }

    public int id() {
        return id;
    }

}
