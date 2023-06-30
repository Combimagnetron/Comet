package me.combimagnetron.lagoon.communication.message.impl.external;

import me.combimagnetron.lagoon.communication.message.impl.servicebound.ServiceBoundMessage;
import me.combimagnetron.lagoon.instance.Instance;

public abstract class ExternalServiceBoundMessage extends ServiceBoundMessage {

    public ExternalServiceBoundMessage(int id, Instance origin, Instance target) {
        super(id, origin, target);
    }
}
