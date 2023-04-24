package me.combimagnetron.lagoon.communication.message.impl.proxybound;

import me.combimagnetron.lagoon.communication.Message;
import me.combimagnetron.lagoon.instance.Instance;

public abstract class ProxyBoundMessage extends Message {
    public ProxyBoundMessage(int id, Instance origin, Instance target) {
        super(id, origin, target);
        writeInt(0x01);
    }

    public ProxyBoundMessage(byte[] bytes) {
        super(bytes);
    }
}
