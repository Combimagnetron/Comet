package me.combimagnetron.comet.communication.message;

import java.util.Collection;

public interface MessageRecipient {

    /**
     * @return Channels currently listening to.
     */
    Collection<MessageChannel> listening();

}
