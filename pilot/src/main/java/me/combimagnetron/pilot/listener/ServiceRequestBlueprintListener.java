package me.combimagnetron.pilot.listener;

import me.combimagnetron.lagoon.communication.MessageHandler;
import me.combimagnetron.lagoon.communication.MessageListener;
import me.combimagnetron.lagoon.communication.message.MessageChannel;
import me.combimagnetron.lagoon.communication.message.impl.servicebound.ServiceBoundRequestInstanceBlueprintsMessage;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.instance.InstanceBlueprint;
import me.combimagnetron.lagoon.util.VersionCollection;
import me.combimagnetron.pilot.Pilot;
import org.kohsuke.github.GHBranch;
import org.kohsuke.github.GHRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@MessageHandler(filter = ServiceBoundRequestInstanceBlueprintsMessage.class, channel = "service:pilot")
public class ServiceRequestBlueprintListener implements MessageListener<ServiceBoundRequestInstanceBlueprintsMessage> {
    @Override
    public void send(ServiceBoundRequestInstanceBlueprintsMessage message) {

    }

    @Override
    public void receive(ServiceBoundRequestInstanceBlueprintsMessage message) {
        final Identifier identifier = message.identifier();
        final String type = message.type().name().toLowerCase();
        String version = message.version();
        GHRepository repository;
        try {
            repository = Pilot.gitHub().getRepository(identifier.namespace().string() + "/" + identifier.key().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (version.equals("latest")) {
            try {
                version = latestVersion(repository, type);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        String versionPrefix = "release(" + version + "): ";
        GHBranch branch;
        try {
            branch = repository.getBranches().entrySet().stream().filter(entry -> entry.getKey().startsWith(versionPrefix)).findAny().orElse(null).getValue();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        InstanceBlueprint blueprint;
        try {
            blueprint = new InstanceBlueprint(InstanceBlueprint.Info.info(Identifier.of(repository.getOwnerName(), repository.getName()), branch.getName(), branch.getOwner().getCreatedAt(), ""));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        VersionCollection<InstanceBlueprint> blueprintVersionCollection = VersionCollection.of(blueprint);
        MessageChannel messageChannel = Pilot.pilot().messageClient().channel(Identifier.of("service", "pilot")).async();
        ServiceBoundRequestInstanceBlueprintsMessage.Response response = new ServiceBoundRequestInstanceBlueprintsMessage.Response(blueprintVersionCollection);
        messageChannel.send(response).async();
    }

    private String latestVersion(GHRepository ghRepository, String type) throws IOException {
        Map<Integer, GHBranch> intMap = new HashMap<>();
        ghRepository.getBranches().forEach((key, value) -> {
            int intKey = Integer.parseInt(extractVersion(key, type));
            intMap.put(intKey, value);
        });
        return extractVersion(intMap.get(intMap.keySet().stream().flatMapToInt(IntStream::of).max().orElse(0)).getName(), type);
    }

    private String extractVersion(String string, String type) {
        return string.split(":")[0].replace(type + "(", "").replace(")", "").replace(".", "");
    }

    @Override
    public void intercept(ServiceBoundRequestInstanceBlueprintsMessage message) {

    }
}
