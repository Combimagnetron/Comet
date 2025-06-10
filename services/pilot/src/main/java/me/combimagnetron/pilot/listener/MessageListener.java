package me.combimagnetron.pilot.listener;

import me.combimagnetron.comet.event.EventBus;
import me.combimagnetron.comet.event.impl.internal.MessageEvent;
import me.combimagnetron.comet.service.Deployment;
import me.combimagnetron.generated.deploymentservice.DeployServiceMessage;
import me.combimagnetron.pilot.Pilot;

public class MessageListener {

    public MessageListener() {
        EventBus.message(DeployServiceMessage.class, this::receive);

    }

    public void receive(MessageEvent<DeployServiceMessage> message) {
        Pilot.pilot().deploy(Deployment.of(message.message().deployment()));
    }

}
