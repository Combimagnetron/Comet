package me.combimagnetron.lagoon.communication.message.impl.servicebound;

import me.combimagnetron.lagoon.communication.Message;
import me.combimagnetron.lagoon.instance.Instance;
import me.combimagnetron.lagoon.internal.network.ByteBuffer;

public abstract class ServiceBoundMessage extends Message {
    public ServiceBoundMessage(int id, Instance origin, Instance target) {
        super(id, origin, target);
        write(ByteBuffer.Adapter.INT, 0x02);
    }

    public ServiceBoundMessage(byte[] bytes) {
        super(bytes);
    }
}
