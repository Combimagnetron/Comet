package me.combimagnetron.comet.communication;

import me.combimagnetron.comet.communication.message.MessageChannel;
import me.combimagnetron.comet.data.Identifier;

public record Channels(MessageChannel serviceChannel, MessageChannel chat, MessageClient client) {

    public static Channels of(MessageClient messageClient) {
        return new Channels(messageClient.channel(Identifier.of("service")), messageClient.channel(Identifier.of("chat")), messageClient);
    }

}
