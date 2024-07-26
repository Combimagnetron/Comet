package me.combimagnetron.comet.level;

import me.combimagnetron.comet.CometBase;
import me.combimagnetron.comet.internal.network.ByteBuffer;

public class Level {
    private final static byte MAGIC = 0x3D;
    private final ByteBuffer byteBuffer;

    private Level() {
        this.byteBuffer = ByteBuffer.empty();
        byteBuffer.write(ByteBuffer.Adapter.BYTE, MAGIC);
    }

}
