package me.combimagnetron.lagoon.communication.message.impl.servicebound;

import me.combimagnetron.lagoon.instance.Instance;
import me.combimagnetron.lagoon.service.Service;
import org.jetbrains.annotations.Nullable;

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
        readString();
        this.status = Status.valueOf(readString());
    }

    @Override
    public @Nullable Instance origin() {
        return null;
    }

    @Override
    public void write() {
        writeString(service.identifier().string());
        writeString(status.name());
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
