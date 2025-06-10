package me.combimagnetron.comet.communication.message;

import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.data.Identifier;

import java.util.Collection;

public interface MessageChannel {

    Identifier identifier();

    /**
     * Sends the given packet to the message.
     * @param message to be sent.
     */
    void send(Message message);

    /**
     * @return All the message recipients currently listening to the channel.
     */
    Collection<MessageRecipient> recipients();

}
