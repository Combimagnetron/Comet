package me.combimagnetron.lagoon.communication.message.impl.servicebound;

import me.combimagnetron.lagoon.communication.message.impl.instancebound.InstanceBoundMessage;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.instance.Instance;
import me.combimagnetron.lagoon.instance.InstanceBlueprint;
import me.combimagnetron.lagoon.util.VersionCollection;
import net.kyori.adventure.identity.Identified;
import org.jetbrains.annotations.Nullable;

public class ServiceBoundRequestInstanceBlueprintsMessage extends ServiceBoundMessage {
    private final Identifier identifier;
    private final String version;
    private final Type type;

    public ServiceBoundRequestInstanceBlueprintsMessage(Identifier identifier) {
        super(2, null, null);
        this.identifier = identifier;
        this.version = "latest";
        this.type = Type.RELEASE;
    }

    public ServiceBoundRequestInstanceBlueprintsMessage(Identifier identifier, Type type) {
        super(2, null, null);
        this.identifier = identifier;
        this.version = "latest";
        this.type = type;
    }

    public ServiceBoundRequestInstanceBlueprintsMessage(Identifier identifier, String version) {
        super(2, null, null);
        this.identifier = identifier;
        this.version = version;
        this.type = Type.RELEASE;
    }

    public ServiceBoundRequestInstanceBlueprintsMessage(Identifier identifier, String version, Type type) {
        super(2, null, null);
        this.identifier = identifier;
        this.version = version;
        this.type = type;
    }

    public ServiceBoundRequestInstanceBlueprintsMessage(byte[] bytes) {
        super(bytes);
        final String[] id = readString().split(":");
        this.identifier = Identifier.of(id[0], id[1]);
        this.version = readString();
        this.type = Type.valueOf(readString());
    }

    @Override
    public @Nullable Instance origin() {
        return null;
    }

    @Override
    public void write() {
        writeString(identifier.string());
        writeString(version);
        writeString(type.name());
    }

    public Identifier identifier() {
        return this.identifier;
    }

    public String version() {
        return this.version;
    }

    public Type type() {
        return this.type;
    }

    public enum Type {
        DEV, STAGING, RELEASE
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
            final int size = readInt();
            for (int i = 0; i < size + 1; i++) {
                collection.add((InstanceBlueprint) readObject());
            }
            this.versionCollection = collection;
        }

        @Override
        public @Nullable Instance origin() {
            return null;
        }

        @Override
        public void write() {
            writeInt(versionCollection.size() - 1);
            versionCollection.forEach(this::writeObject);
        }

        public VersionCollection<InstanceBlueprint> versionCollection() {
            return this.versionCollection;
        }

    }

}
