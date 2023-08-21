package me.combimagnetron.lagoon.communication.message.impl.servicebound;

import me.combimagnetron.lagoon.instance.Instance;
import me.combimagnetron.lagoon.internal.network.ByteBuffer;
import me.combimagnetron.lagoon.service.Service;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class ServiceBoundRequestServiceStatusChangeMessage extends ServiceBoundMessage {
    private final Service<?> service;
    private final Status status;

    public ServiceBoundRequestServiceStatusChangeMessage(Service<?> service, Status status) {
        super(1, null, null);
        this.service =  service;
        this.status = status;
    }

    public ServiceBoundRequestServiceStatusChangeMessage(byte[] bytes) {
        super(bytes);
        this.service = null;
        read(ByteBuffer.Adapter.STRING);
        this.status = buffer().readEnum(Status.class);
    }

    @Override
    public @Nullable Instance origin() {
        return null;
    }

    @Override
    public void write() {
        write(ByteBuffer.Adapter.STRING, service.identifier().string());
        write(ByteBuffer.Adapter.INT, status.ordinal());
    }

    public Service<?> service() {
        return service;
    }

    public Status status() {
        return status;
    }

    public enum Status {
        RESTART, START, STOP

    }

}
