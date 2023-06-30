package me.combimagnetron.pilot.listener;

import me.combimagnetron.lagoon.communication.MessageHandler;
import me.combimagnetron.lagoon.communication.MessageListener;
import me.combimagnetron.lagoon.communication.message.impl.servicebound.ServiceBoundRequestServiceStatusChangeMessage;

@MessageHandler(filter = ServiceBoundRequestServiceStatusChangeMessage.class, channel = "service:pilot")
public class ServiceStatusChangeMessageListener implements MessageListener<ServiceBoundRequestServiceStatusChangeMessage> {
    @Override
    public void onSend(ServiceBoundRequestServiceStatusChangeMessage message) {}

    @Override
    public void onReceive(ServiceBoundRequestServiceStatusChangeMessage message) {
    }

    @Override
    public void intercept(ServiceBoundRequestServiceStatusChangeMessage message) {

    }
}
