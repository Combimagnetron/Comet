package me.combimagnetron.lagoon.communication.message.impl.servicebound;

import me.combimagnetron.lagoon.communication.message.impl.instancebound.InstanceBoundMessage;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.instance.Instance;
import me.combimagnetron.lagoon.instance.InstanceBlueprint;
import me.combimagnetron.lagoon.internal.network.ByteBuffer;
import me.combimagnetron.lagoon.util.VersionCollection;
import net.kyori.adventure.identity.Identified;
import org.jetbrains.annotations.Nullable;

public class ServiceBoundRequestInstanceBlueprintsMessage extends ServiceBoundMessage {
    private final Identifier identifier;
    private final String version;
    public ServiceBoundRequestInstanceBlueprintsMessage(Identifier identifier) {
        super(2, null, null);
        this.identifier = identifier;
        this.version = "main";
    }

    public ServiceBoundRequestInstanceBlueprintsMessage(Identifier identifier, String version) {
        super(2, null, null);
        this.identifier = identifier;
        this.version = version;
    }

    public ServiceBoundRequestInstanceBlueprintsMessage(byte[] bytes) {
        super(bytes);
        final String[] id = read(ByteBuffer.Adapter.STRING).split(":");
        this.identifier = Identifier.of(id[0], id[1]);
        this.version = read(ByteBuffer.Adapter.STRING);
    }

    @Override
    public @Nullable Instance origin() {
        return null;
    }

    @Override
    public void write() {
        write(ByteBuffer.Adapter.STRING, identifier.string());
        write(ByteBuffer.Adapter.STRING, version);
    }

    public Identifier identifier() {
        return this.identifier;
    }

    public String version() {
        return this.version;
    }

    public static class Response extends InstanceBoundMessage {
        private final VersionCollection<InstanceBlueprint> versionCollection;

        public Response(VersionCollection<InstanceBlueprint> versionCollection) {
            super(2, null, null);
            this.versionCollection = versionCollection;
        }

        public Response(byte[] bytes) {
            super(bytes);
            final VersionCollection<InstanceBlueprint> collection = new VersionCollection<>();
            final int size = read(ByteBuffer.Adapter.INT);
            for (int i = 0; i < size + 1; i++) {
                //collection.add((InstanceBlueprint) readObject());
            }
            this.versionCollection = collection;
        }

        @Override
        public @Nullable Instance origin() {
            return null;
        }

        @Override
        public void write() {
            write(ByteBuffer.Adapter.INT, versionCollection.size() - 1);
            //versionCollection.forEach(this::writeObject);
        }

        public VersionCollection<InstanceBlueprint> versionCollection() {
            return this.versionCollection;
        }

    }

}
