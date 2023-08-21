package me.combimagnetron.lagoon.communication.message.impl.servicebound;

import me.combimagnetron.lagoon.communication.Message;
import me.combimagnetron.lagoon.instance.Instance;
import me.combimagnetron.lagoon.internal.network.ByteBuffer;
import org.jetbrains.annotations.Nullable;

public class ServiceBoundRegisterInstanceMessage extends ServiceBoundMessage {
    private final Instance instance;

    public ServiceBoundRegisterInstanceMessage(Instance origin) {
        super(0x00, origin, null);
        this.instance = origin;
        write();
    }

    public ServiceBoundRegisterInstanceMessage(byte[] bytes) {
        super(bytes);
        this.instance = null; //readUUID();
    }

    @Override
    public @Nullable Instance origin() {
        return instance;
    }

    @Override
    public void write() {
        write(ByteBuffer.Adapter.UUID, instance.uniqueIdentifier());
    }
}
