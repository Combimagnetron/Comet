package me.combimagnetron.lagoon.communication.message.impl.servicebound;

import me.combimagnetron.lagoon.communication.message.impl.instancebound.InstanceBoundMessage;
import me.combimagnetron.lagoon.instance.Instance;
import org.jetbrains.annotations.Nullable;

public class ServiceBoundRequestInstanceBlueprintsMessage extends ServiceBoundMessage {
    public ServiceBoundRequestInstanceBlueprintsMessage() {
        super(2, null, null);
    }

    public ServiceBoundRequestInstanceBlueprintsMessage(byte[] bytes) {
        super(bytes);
    }

    @Override
    public @Nullable Instance origin() {
        return null;
    }

    @Override
    public void write() {

    }

    public class Response extends InstanceBoundMessage {

        public Response() {
            super(2, null, null);
        }

        public Response(byte[] bytes) {
            super(bytes);
        }

        @Override
        public @Nullable Instance origin() {
            return null;
        }

        @Override
        public void write() {

        }
    }

}
