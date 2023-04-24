package me.combimagnetron.lagoon.event;

public interface Event {

    Class<? extends Event> eventType();

}
