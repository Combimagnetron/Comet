package me.combimagnetron.comet.internal.network;

import me.combimagnetron.comet.internal.network.packet.ClientPacket;
import me.combimagnetron.comet.internal.network.packet.Packet;
import me.combimagnetron.comet.internal.network.packet.ServerPacket;

import java.util.Collection;
import java.util.HashMap;

public abstract class VersionRegistry {
    private static final HashMap<Integer, Class<? extends ClientPacket>> CLIENT = new HashMap<>();
    private static final HashMap<Integer, Class<? extends ServerPacket>> SERVER = new HashMap<>();

    public static Class<? extends ClientPacket> client(int id) {
        return CLIENT.get(id);
    }

    public static Class<? extends ServerPacket> server(int id) {
        return SERVER.get(id);
    }

    public static Class<? extends Packet> packet(int id, Entry.Type type) {
        return type == Entry.Type.CLIENT ? CLIENT.get(id) : SERVER.get(id);
    }

    public static void client(Collection<Entry<? extends ClientPacket>> collection) {
        collection.forEach(entry -> CLIENT.put(entry.id(), entry.clazz()));
    }

    public static void server(Collection<Entry<? extends ServerPacket>> collection) {
        collection.forEach(entry -> SERVER.put(entry.id(), entry.clazz()));
    }

    public interface Entry<T extends Packet> {

        static <T extends Packet> Entry<T> of(Class<T> clazz, int id, Type type) {
            return new Impl<>(clazz, id, type);
        }

        enum Type {
            CLIENT, SERVER
        }

        Class<T> clazz();

        int id();

        record Impl<T extends Packet>(Class<T> clazz, int id, Type type) implements Entry<T> {

            public static <T extends Packet> Impl<T> of(Class<T> clazz, int id, Type type) {
                return new Impl<>(clazz, id, type);
            }

        }

    }

}
