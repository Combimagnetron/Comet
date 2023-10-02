package me.combimagnetron.comet.internal.network.sniffer;

import me.combimagnetron.comet.internal.network.packet.Packet;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

public interface Sniffer {

    <T extends Packet> Node<T> node(String name);

    void call(Packet container);

    class Impl implements Sniffer {
        private final Map<String, Node<Packet>> nodeMap = new TreeMap<>();

        @Override
        public Node<Packet> node(String name) {
            return nodeMap.computeIfAbsent(name, Node::new);
        }

        @Override
        public void call(Packet container) {
            nodeMap.forEach((string, node) -> node.post(container));
        }
    }

    class Node<T extends Packet> {
        private final Map<Packet.Type<T>, Ticket<T>> ticketMap = new HashMap<>();
        private final String name;

        protected Node(String name) {
            this.name = name;
        }

        public Ticket<T> subscribe(Packet.Type<T> type) {
            final Ticket<T> ticket = new Ticket<>();
            this.ticketMap.put(type, ticket);
            return ticket;
        }

        public void post(T container) {
            ticketMap.entrySet().stream()
                    .filter(typeTicketEntry -> typeTicketEntry.getKey().clazz().equals(container.getClass()))
                    .forEach(typeTicketEntry -> typeTicketEntry.getValue().receiveConsumer.accept(container));
        }

    }

    class Ticket<T extends Packet> {
        private Consumer<T> sendConsumer = event -> {};
        private Consumer<T> receiveConsumer = event -> {};

        public Ticket<T> receive(Consumer<T> receiveConsumer) {
            this.receiveConsumer = receiveConsumer;
            return this;
        }

        public Ticket<T> send(Consumer<T> sendConsumer) {
            this.receiveConsumer = sendConsumer;
            return this;
        }

    }


}
