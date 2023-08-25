package me.combimagnetron.lagoon.communication.serializer.impl;

import me.combimagnetron.lagoon.communication.serializer.Adapter;
import me.combimagnetron.lagoon.file.File;
import me.combimagnetron.lagoon.file.Folder;
import me.combimagnetron.lagoon.internal.network.ByteBuffer;

public class FolderAdapter implements Adapter<Folder> {
    private ByteBuffer byteBuffer;
    @Override
    public Folder deserialize(byte[] bytes) {
        byteBuffer = ByteBuffer.of(bytes);
        int amount = byteBuffer.read(ByteBuffer.Adapter.INT);
        String name = byteBuffer.read(ByteBuffer.Adapter.STRING);
        File<?>[] files = new File[amount];
        for (int i = 0; i < amount; i++) {
            //files[i] = (File<?>) readObject();
        }
        return Folder.of(name, files);
    }

    @Override
    public byte[] serialize(Folder object) {
        byteBuffer = ByteBuffer.empty();
        byteBuffer.write(ByteBuffer.Adapter.INT, object.content().size());
        byteBuffer.write(ByteBuffer.Adapter.STRING, object.name());
        //object.content().forEach(this::writeObject);
        return byteBuffer.bytes();
    }
}
