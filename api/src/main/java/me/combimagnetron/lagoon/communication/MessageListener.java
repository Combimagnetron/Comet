package me.combimagnetron.lagoon.communication;

public interface MessageListener<T extends Message> {
    void send(T message);

    void receive(T message);

    void intercept(T message);

}
