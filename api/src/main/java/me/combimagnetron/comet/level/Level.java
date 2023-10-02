package me.combimagnetron.comet.level;

import me.combimagnetron.comet.CometBase;
import me.combimagnetron.comet.internal.network.ByteBuffer;

import javax.inject.Inject;

public class Level {
    @Inject
    CometBase<?> cometBase;
    private final static byte MAGIC = 0x3D;
    private final ByteBuffer byteBuffer;

    private Level() {
        this.byteBuffer = ByteBuffer.empty();
        byteBuffer.write(ByteBuffer.Adapter.BYTE, MAGIC);
    }

}
