package me.combimagnetron.lagoon.communication.message.impl.instancebound;

import me.combimagnetron.lagoon.communication.Message;
import me.combimagnetron.lagoon.instance.Instance;
import org.jetbrains.annotations.Nullable;

public abstract class InstanceBoundMessage extends Message {
    public InstanceBoundMessage(int id, Instance origin, Instance target) {
        super(id, origin, target);
        writeInt(0x00);
    }

    public InstanceBoundMessage(byte[] bytes) {
        super(bytes);
    }
}
