package me.combimagnetron.lagoon.communication.message;

import me.combimagnetron.lagoon.communication.Message;
import me.combimagnetron.lagoon.data.Identifier;
import me.combimagnetron.lagoon.operation.Operation;

import java.util.Collection;

public interface MessageChannel {

    Identifier identifier();

    /**
     * Sends the given packet to the message.
     * @param The packet to be sent.
     * @return Simple operation which runs the sending process when called.
     */
    Operation<Void> send(Message abstractCustomPacket);

    /**
     * @return All the message recipients currently listening to the channel.
     */
    Operation<Collection<MessageRecipient>> receipients();


}
