package me.combimagnetron.comet.communication;

import me.combimagnetron.comet.communication.message.MessageChannel;

public record Channels(MessageChannel serviceChannel, MessageChannel chat, MessageClient client) {
}
