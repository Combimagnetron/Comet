package me.combimagnetron.comet.communication.message;

import me.combimagnetron.comet.communication.Message;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.operation.Operation;

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
