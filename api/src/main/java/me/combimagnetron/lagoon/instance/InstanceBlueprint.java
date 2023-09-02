package me.combimagnetron.lagoon.instance;

import me.combimagnetron.lagoon.Comet;
import me.combimagnetron.lagoon.communication.message.MessageChannel;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.util.VersionCollection;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import static me.combimagnetron.lagoon.operation.Operations.async;

public class InstanceBlueprint implements Serializable {
    private final Info info;
    private VersionCollection<InstanceBlueprint> versionCollection;

    public InstanceBlueprint(Info info) {
        this.info = info;
    }

    public record Info(Identifier identifier, String version, Date created, String author) implements Serializable {
        public static Info info(Identifier identifier, String version, Date created, String author) {
            return new Info(identifier, version, created, author);
        }
    }

    public static VersionCollection<InstanceBlueprint> request(Identifier identifier) {
        final MessageChannel channel = Comet.messageClient().channel(Identifier.of("service", "pilot"));
        //async(channel.send(//new ServiceBoundRequestInstanceBlueprintsMessage(identifier)));
        AtomicReference<VersionCollection<InstanceBlueprint>> atomicReference = new AtomicReference<>();
        //channel.awaitMessage(//ServiceBoundRequestInstanceBlueprintsMessage.Response.class, (message) -> {
            //atomicReference.set(((ServiceBoundRequestInstanceBlueprintsMessage.Response) message).versionCollection());
        //});
        return atomicReference.get();
    }

}
