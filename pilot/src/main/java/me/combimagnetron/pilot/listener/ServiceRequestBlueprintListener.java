package me.combimagnetron.pilot.listener;

import me.combimagnetron.lagoon.communication.MessageHandler;
import me.combimagnetron.lagoon.communication.MessageListener;
import me.combimagnetron.lagoon.communication.message.impl.servicebound.ServiceBoundRequestInstanceBlueprintsMessage;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.instance.InstanceBlueprint;
import me.combimagnetron.pilot.Pilot;
import org.kohsuke.github.GHRepository;

import java.io.IOException;

@MessageHandler(filter = ServiceBoundRequestInstanceBlueprintsMessage.class, channel = "service:pilot")
public class ServiceRequestBlueprintListener implements MessageListener<ServiceBoundRequestInstanceBlueprintsMessage> {
    @Override
    public void send(ServiceBoundRequestInstanceBlueprintsMessage message) {

    }

    @Override
    public void receive(ServiceBoundRequestInstanceBlueprintsMessage message) {
        final Identifier identifier = message.identifier();
        final String version = message.version();
        GHRepository repository;
        try {
            repository = Pilot.gitHub().getRepository(identifier.namespace().string() + "/" + identifier.key().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        InstanceBlueprint blueprint = new InstanceBlueprint(InstanceBlueprint.Info.info(null, null, null, null));
    }

    @Override
    public void intercept(ServiceBoundRequestInstanceBlueprintsMessage message) {

    }
}
