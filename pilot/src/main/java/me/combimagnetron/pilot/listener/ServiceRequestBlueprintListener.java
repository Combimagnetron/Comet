package me.combimagnetron.pilot.listener;

import me.combimagnetron.lagoon.communication.MessageHandler;
import me.combimagnetron.lagoon.communication.MessageListener;
import me.combimagnetron.lagoon.communication.message.impl.servicebound.ServiceBoundRequestInstanceBlueprintsMessage;

@MessageHandler(filter = ServiceBoundRequestInstanceBlueprintsMessage.class, channel = "service:pilot")
public class ServiceRequestBlueprintListener implements MessageListener<ServiceBoundRequestInstanceBlueprintsMessage> {
    @Override
    public void onSend(ServiceBoundRequestInstanceBlueprintsMessage message) {

    }

    @Override
    public void onReceive(ServiceBoundRequestInstanceBlueprintsMessage message) {

    }

    @Override
    public void intercept(ServiceBoundRequestInstanceBlueprintsMessage message) {

    }
}
