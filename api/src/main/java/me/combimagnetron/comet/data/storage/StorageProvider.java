package me.combimagnetron.comet.data.storage;

import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.internal.network.ByteBuffer;

public interface StorageProvider {

    void save(Identifier identifier, ByteBuffer data);

    ByteBuffer load(Identifier identifier);

    void delete(Identifier identifier);

}
