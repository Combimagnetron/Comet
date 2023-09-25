package me.combimagnetron.lagoon.level;

import me.combimagnetron.lagoon.CometBase;
import me.combimagnetron.lagoon.internal.network.ByteBuffer;

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
