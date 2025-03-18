package me.combimagnetron.comet.data.storage.s3;

import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.data.storage.StorageProvider;
import me.combimagnetron.comet.internal.network.ByteBuffer;

public class S3StorageProvider implements StorageProvider {

    @Override
    public void save(Identifier identifier, ByteBuffer data) {

    }

    @Override
    public ByteBuffer load(Identifier identifier) {
        return null;
    }

    @Override
    public void delete(Identifier identifier) {

    }
}
