package me.combimagnetron.lagoon.communication.message;

import me.combimagnetron.lagoon.communication.Message;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.operation.Operation;

import java.util.Collection;
import java.util.function.Consumer;

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

    Operation<Void> await(Class<? extends Message> type, Consumer<Message> execute);

}
