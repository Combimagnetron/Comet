package me.combimagnetron.lagoon.communication.message.impl.servicebound;

import me.combimagnetron.lagoon.instance.Instance;
import org.jetbrains.annotations.Nullable;

public class ServiceBoundTestIncrementMessage extends ServiceBoundMessage {
    private int i = 0;

    public ServiceBoundTestIncrementMessage() {
        super(0x01, null, null);
        write();
    }

    public ServiceBoundTestIncrementMessage(byte[] bytes) {
        super(bytes);
        i = readInt();
    }

    @Override
    public @Nullable Instance origin() {
        return null;
    }

    @Override
    public void write() {
        writeInt(i++);
    }

    public int i() {
        return i;
    }
}
