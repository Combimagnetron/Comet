package me.combimagnetron.lagoon.communication.serializer.impl;

import me.combimagnetron.lagoon.communication.serializer.Adapter;
import me.combimagnetron.lagoon.file.File;
import me.combimagnetron.lagoon.file.Folder;

public class FolderAdapter extends ByteBuffer implements Adapter<Folder> {
    @Override
    public Folder deserialize(byte[] bytes) {
        read(bytes);
        int amount = readInt();
        String name = readString();
        File<?>[] files = new File[amount];
        for (int i = 0; i < amount; i++) {
            files[i] = (File<?>) readObject();
        }
        return Folder.of(name, files);
    }

    @Override
    public byte[] serialize(Folder object) {
        writeInt(object.content().size());
        writeString(object.name());
        object.content().forEach(this::writeObject);
        return toBytes();
    }
}
