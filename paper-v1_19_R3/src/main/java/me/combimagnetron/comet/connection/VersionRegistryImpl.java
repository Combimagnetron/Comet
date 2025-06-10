package me.combimagnetron.comet.connection;

import me.combimagnetron.comet.internal.network.VersionRegistry;
import me.combimagnetron.comet.internal.network.packet.ClientPacket;
import me.combimagnetron.comet.internal.network.packet.ServerPacket;
import me.combimagnetron.comet.internal.network.packet.client.*;
import me.combimagnetron.comet.internal.network.packet.server.ServerClickContainer;
import me.combimagnetron.comet.util.Values;

public class VersionRegistryImpl extends VersionRegistry {
    public static final Entry<ClientBundleDelimiter> BUNDLE_DELIMITER = Entry.of(ClientBundleDelimiter.class, 0x00, Entry.Type.CLIENT);
    public static final Entry<ClientSpawnEntity> SPAWN_ENTITY = Entry.of(ClientSpawnEntity.class, 0x01, Entry.Type.CLIENT);
    public static final Entry<ClientSetScreenContent> SET_SCREEN_CONTENT = Entry.of(ClientSetScreenContent.class, 0x13, Entry.Type.CLIENT);
    public static final Entry<ClientSetScreenSlot> SET_SCREEN_SLOT = Entry.of(ClientSetScreenSlot.class, 0x15, Entry.Type.CLIENT);
    public static final Entry<ClientOpenScreen> CLIENT_OPEN_WINDOW = Entry.of(ClientOpenScreen.class, 0x33, Entry.Type.CLIENT);
    public static final Entry<ClientEntityMetadata> CLIENT_ENTITY_METADATA = Entry.of(ClientEntityMetadata.class, 0x56, Entry.Type.CLIENT);
    public static final Values<Entry<? extends ClientPacket>> CLIENT = Values.of(BUNDLE_DELIMITER, SPAWN_ENTITY, SET_SCREEN_CONTENT, SET_SCREEN_SLOT, CLIENT_OPEN_WINDOW, CLIENT_ENTITY_METADATA);
    public static final Entry<ServerClickContainer> CLICK_CONTAINER = Entry.of(ServerClickContainer.class, 0x0D, Entry.Type.SERVER);
    public static final Values<Entry<? extends ServerPacket>> SERVER = Values.of(CLICK_CONTAINER);

    static {
        client(CLIENT.values());
        server(SERVER.values());
    }

}
