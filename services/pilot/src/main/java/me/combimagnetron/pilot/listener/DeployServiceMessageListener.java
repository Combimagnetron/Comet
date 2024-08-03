package me.combimagnetron.pilot.listener;

import me.combimagnetron.comet.communication.MessageHandler;
import me.combimagnetron.comet.communication.MessageListener;
import me.combimagnetron.generated.DeployServiceMessage;
import me.combimagnetron.pilot.Pilot;

@MessageHandler(filter = DeployServiceMessage.class)
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
