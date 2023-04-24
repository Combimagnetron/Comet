package me.combimagnetron.lagoon.communication;

public interface MessageListener<T extends Message> {
    void onSend(T message);

    void onReceive(T message);

    void intercept(T message);

}
