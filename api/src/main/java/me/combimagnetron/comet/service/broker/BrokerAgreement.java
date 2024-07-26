package me.combimagnetron.comet.service.broker;

import me.combimagnetron.comet.communication.Message;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BrokerAgreement {
    private final Set<MessageReference.MonitorMessageReference<?>> monitorMessages = new HashSet<>();
    private final Set<MessageReference.InterceptMessageReference<?>> interceptMessages = new HashSet<>();

    private BrokerAgreement() {
    }

    public static BrokerAgreement brokerAgreement() {
        return new BrokerAgreement();
    }

    public BrokerAgreement monitor(MessageReference.MonitorMessageReference<?>... messages) {
        monitorMessages.addAll(List.of(messages));
        return this;
    }

    public BrokerAgreement intercept(MessageReference.InterceptMessageReference<?>... messages) {
        interceptMessages.addAll(List.of(messages));
        return this;
    }

    public int monitorSize() {
        return monitorMessages.size();
    }

    public int interceptSize() {
        return interceptMessages.size();
    }

    public Set<MessageReference.MonitorMessageReference<?>> monitorMessages() {
        return monitorMessages;
    }

    public Set<MessageReference.InterceptMessageReference<?>> interceptMessages() {
        return interceptMessages;
    }

    public interface MessageReference {

        Class<? extends Message> message();

        record MonitorMessageReference<T extends Message>(Class<T> message) implements MessageReference {
        }

        record InterceptMessageReference<T extends Message>(Class<T> message) implements MessageReference {
        }

    }

}
