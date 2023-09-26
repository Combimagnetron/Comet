package me.combimagnetron.pilot.listener;

import me.combimagnetron.generated.DeployServiceMessage;
import me.combimagnetron.lagoon.communication.MessageHandler;
import me.combimagnetron.lagoon.communication.MessageListener;
import me.combimagnetron.pilot.Pilot;

@MessageHandler(filter = DeployServiceMessage.class, channel = "service:handler")
public class DeployServiceMessageListener implements MessageListener<DeployServiceMessage> {
    private final Pilot pilot = Pilot.pilot();

    @Override
    public void send(DeployServiceMessage message) {
    }

    @Override
    public void receive(DeployServiceMessage message) {
        pilot.deploy(message.deployment());
    }

    @Override
    public void intercept(DeployServiceMessage message) {

    }
}
