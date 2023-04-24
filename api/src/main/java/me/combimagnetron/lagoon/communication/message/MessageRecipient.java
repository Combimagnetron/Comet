package me.combimagnetron.lagoon.communication.message;

import java.util.Collection;

public interface MessageRecipient {

    /**
     * @return Channels currently listening to.
     */
    Collection<MessageChannel> listening();

}
