package me.combimagnetron.comet.instance;

import me.combimagnetron.comet.Comet;
import me.combimagnetron.comet.communication.message.MessageChannel;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.util.VersionCollection;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

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
