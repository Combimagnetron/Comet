package me.combimagnetron.comet.communication.message.user;

import me.combimagnetron.comet.CometBase;
import me.combimagnetron.comet.communication.message.MessageChannel;
import me.combimagnetron.comet.communication.message.redis.RedisMessageChannel;
import me.combimagnetron.comet.communication.message.redis.RedisMessageClient;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.service.broker.BrokerAgreement;
import me.combimagnetron.comet.service.broker.Brokered;
import me.combimagnetron.comet.user.User;

import java.util.HashSet;
import java.util.Set;

public interface UserMessageChannel extends MessageChannel {

    User<?> user();

    Set<BrokerAgreement> brokerAgreements();

    static UserMessageChannel redis(Identifier identifier, User<?> user) {
        return new RedisUserMessageChannel((RedisMessageClient) CometBase.comet().channels().client(), identifier, user);
    }

    class RedisUserMessageChannel extends RedisMessageChannel implements UserMessageChannel {
        private final User<?> user;
        private final Set<BrokerAgreement> brokerAgreements = new HashSet<>();

        protected RedisUserMessageChannel(RedisMessageClient client, Identifier identifier, User<?> user) {
            super(client, identifier);
            this.user = user;
        }

        @Override
        public User<?> user() {
            return user;
        }

        @Override
        public Set<BrokerAgreement> brokerAgreements() {
            return brokerAgreements;
        }
    }

}
