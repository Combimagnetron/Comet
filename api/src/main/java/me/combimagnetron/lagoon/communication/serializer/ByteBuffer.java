package me.combimagnetron.lagoon.communication.serializer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import java.io.*;
import java.util.UUID;

public abstract class ByteBuffer {
    private ByteArrayDataInput byteArrayDataInput;
    private ByteArrayDataOutput byteArrayDataOutput;

    private void checkNotNull() {
        if (byteArrayDataOutput == null) byteArrayDataOutput = ByteStreams.newDataOutput();
    }

    public void read(byte[] bytes) {
        byteArrayDataInput = ByteStreams.newDataInput(bytes);
    }

    protected void writeString(String string) {
        checkNotNull();
        byteArrayDataOutput.writeUTF(string);
    }

    protected void writeUUID(UUID uuid) {
        checkNotNull();
        byteArrayDataOutput.writeLong(uuid.getMostSignificantBits());
        byteArrayDataOutput.writeLong(uuid.getLeastSignificantBits());
    }

    protected void writeAdventureComponent(Component component) {
        checkNotNull();
        byteArrayDataOutput.writeUTF(GsonComponentSerializer.gson().serialize(component));
    }

    protected void writeChar(char chr) {
        checkNotNull();
        byteArrayDataOutput.writeChar(chr);
    }

    protected void writeDouble(double dbl) {
        checkNotNull();
        byteArrayDataOutput.writeDouble(dbl);
    }

    protected void writeFloat(float flt) {
        checkNotNull();
        byteArrayDataOutput.writeFloat(flt);
    }

    protected void writeLong(long lng) {
        checkNotNull();
        byteArrayDataOutput.writeLong(lng);
    }

    protected void writeInt(int i) {
        checkNotNull();
        byteArrayDataOutput.writeInt(i);
    }

    protected void writeShort(short shrt) {
        checkNotNull();
        byteArrayDataOutput.writeShort(shrt);
    }

    protected void writeByteArray(byte... bytes) {
        checkNotNull();
        byteArrayDataOutput.write(bytes.length);
        byteArrayDataOutput.write(bytes);
    }

    protected void writeByte(byte bte) {
        checkNotNull();
        byteArrayDataOutput.write(bte);
    }

    protected void writeBoolean(boolean bool) {
        checkNotNull();
        byteArrayDataOutput.writeBoolean(bool);
    }

    protected void writeObject(Object object) {
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

    protected String readString() {
        return byteArrayDataInput.readUTF();
    }

    protected UUID readUUID() {
        return new UUID(byteArrayDataInput.readLong(), byteArrayDataInput.readLong());
    }

    protected Component readAdventureComponent() {
        return GsonComponentSerializer.gson().deserialize(readString());
    }

    protected char readChar() {
        return byteArrayDataInput.readChar();
    }

    protected double readDouble() {
        return byteArrayDataInput.readDouble();
    }

    protected float readFloat() {
        return byteArrayDataInput.readFloat();
    }

    protected long readLong() {
        return byteArrayDataInput.readLong();
    }

    protected int readInt() {
        return byteArrayDataInput.readInt();
    }

    protected short readShort() {
        return byteArrayDataInput.readShort();
    }

    protected byte[] readByteArray(byte... bytes) {
        int arraySize = readInt();
        byte[] byteArray = new byte[arraySize];
        for (int i = 0; i < arraySize; i++)
            byteArray[i] = readByte();

        return byteArray;
    }

    protected byte readByte() {
        return byteArrayDataInput.readByte();
    }

    protected boolean readBoolean() {
        return byteArrayDataInput.readBoolean();
    }

    protected Object readObject() {
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
