package me.combimagnetron.lagoon.communication.message.impl.proxybound;

import me.combimagnetron.lagoon.communication.Message;
import me.combimagnetron.lagoon.instance.Instance;
import me.combimagnetron.lagoon.internal.network.ByteBuffer;

public abstract class ProxyBoundMessage extends Message {
    public ProxyBoundMessage(int id, Instance origin, Instance target) {
        super(id, origin, target);
        write(ByteBuffer.Adapter.INT, 0x01);
    }

    public ProxyBoundMessage(byte[] bytes) {
        super(bytes);
    }
}
