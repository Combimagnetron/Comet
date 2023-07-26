package me.combimagnetron.lagoon.communication.serializer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ByteBuffer {
    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;
    private ByteArrayDataInput byteArrayDataInput;
    private ByteArrayDataOutput byteArrayDataOutput;

    private void checkNotNull() {
        if (byteArrayDataOutput == null) byteArrayDataOutput = ByteStreams.newDataOutput();
    }

    public void read(byte[] bytes) {
        byteArrayDataInput = ByteStreams.newDataInput(bytes);
    }

    public void writeString(String string) {
        checkNotNull();
        byteArrayDataOutput.writeUTF(string);
    }

    public void writeUUID(UUID uuid) {
        checkNotNull();
        byteArrayDataOutput.writeLong(uuid.getMostSignificantBits());
        byteArrayDataOutput.writeLong(uuid.getLeastSignificantBits());
    }

    public void writeAdventureComponent(Component component) {
        checkNotNull();
        byteArrayDataOutput.writeUTF(GsonComponentSerializer.gson().serialize(component));
    }

    public void writeChar(char chr) {
        checkNotNull();
        byteArrayDataOutput.writeChar(chr);
    }

    public void writeDouble(double dbl) {
        checkNotNull();
        byteArrayDataOutput.writeDouble(dbl);
    }

    public void writeFloat(float flt) {
        checkNotNull();
        byteArrayDataOutput.writeFloat(flt);
    }

    public void writeLong(long lng) {
        checkNotNull();
        byteArrayDataOutput.writeLong(lng);
    }

    public void writeInt(int i) {
        checkNotNull();
        byteArrayDataOutput.writeInt(i);
    }

    public void writeShort(short shrt) {
        checkNotNull();
        byteArrayDataOutput.writeShort(shrt);
    }

    public void writeByteArray(byte... bytes) {
        checkNotNull();
        byteArrayDataOutput.write(bytes.length);
        byteArrayDataOutput.write(bytes);
    }

    public void writeByte(byte bte) {
        checkNotNull();
        byteArrayDataOutput.write(bte);
    }

    public void writeBoolean(boolean bool) {
        checkNotNull();
        byteArrayDataOutput.writeBoolean(bool);
    }

    public void writeVarInt(int value) {
        while (true) {
            if ((value & ~SEGMENT_BITS) == 0) {
                writeByte((byte) value);
                return;
            }

            writeByte((byte) ((value & SEGMENT_BITS) | CONTINUE_BIT));

            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
        }
    }

    public void writeObject(Object object) {
        checkNotNull();
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            writeByteArray(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readString() {
        return byteArrayDataInput.readUTF();
    }

    public UUID readUUID() {
        return new UUID(byteArrayDataInput.readLong(), byteArrayDataInput.readLong());
    }

    public Component readAdventureComponent() {
        return GsonComponentSerializer.gson().deserialize(readString());
    }

    public char readChar() {
        return byteArrayDataInput.readChar();
    }

    public double readDouble() {
        return byteArrayDataInput.readDouble();
    }

    public float readFloat() {
        return byteArrayDataInput.readFloat();
    }

    public long readLong() {
        return byteArrayDataInput.readLong();
    }

    public int readInt() {
        return byteArrayDataInput.readInt();
    }

    public short readShort() {
        return byteArrayDataInput.readShort();
    }

    public byte[] readByteArray() {
        int arraySize = readInt();
        byte[] byteArray = new byte[arraySize];
        for (int i = 0; i < arraySize; i++)
            byteArray[i] = readByte();
        return byteArray;
    }

    public byte[] readByteArray(int length) {
        byte[] byteArray = new byte[length];
        for (int i = 0; i < length; i++)
            byteArray[i] = readByte();
        return byteArray;
    }

    public <T> void writeCollection(Collection<T> values, BiConsumer<ByteBuffer, T> consumer) {
        if (values == null) {
            writeByte((byte) 0);
            return;
        }
        writeVarInt(values.size());
        for (T value : values) {
            consumer.accept(this, value);
        }
    }

    public <T>  Collection<T> readCollection(Function<ByteBuffer, T> function) {
        final int size = readInt();
        List<T> values = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            values.add(function.apply(this));
        }
        return values;
    }

    public int readVarInt() {
        int value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = readByte();
            value |= (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;

            if (position >= 32) throw new RuntimeException("VarInt is too big");
        }

        return value;
    }

    public byte readByte() {
        return byteArrayDataInput.readByte();
    }

    public boolean readBoolean() {
        return byteArrayDataInput.readBoolean();
    }

    public Object readObject() {
        byte[] bytes = readByteArray();
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             ObjectInput objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] toBytes() {
        return byteArrayDataOutput.toByteArray();
    }

}
