package me.combimagnetron.comet;

import me.combimagnetron.comet.event.EventBus;
import me.combimagnetron.comet.event.EventSubscription;
import me.combimagnetron.comet.event.impl.internal.MessageEvent;
import me.combimagnetron.comet.internal.network.ByteBuffer;
import me.combimagnetron.comet.user.User;
import me.combimagnetron.comet.user.UserHandler;
import me.combimagnetron.generated.RequestUserMessage;
import me.combimagnetron.generated.UserInfoResponseMessage;
import me.combimagnetron.generated.UserJoinNetworkMessage;
import me.combimagnetron.generated.UserLeaveNetworkMessage;
import me.combimagnetron.generated.entityservice.EntityService;
import net.kyori.adventure.audience.Audience;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class RemoteUserHandlerImpl implements UserHandler<Audience> {
    private final Map<UUID, User<Audience>> users = new ConcurrentHashMap<>();

    public RemoteUserHandlerImpl() {
        //EventBus.message(UserJoin.class, this::join);
        EventBus.message(UserLeaveNetworkMessage.class, this::leave);
    }

    private void join(MessageEvent<UserJoinNetworkMessage> event) {
        UserJoinNetworkMessage message = event.message();
        User<?> user = requestUserInfo(message.uuid());
        users.put(user.uniqueIdentifier(), (User<Audience>) user);
    }

    private void leave(MessageEvent<UserLeaveNetworkMessage> event) {
        UserLeaveNetworkMessage message = event.message();
        users.remove(message.uuid());
    }

    public User<?> requestUserInfo(UUID uuid) {
        CometBase.comet().channels().serviceChannel().send(RequestUserMessage.of(uuid));
        try {
            return CompletableFuture.supplyAsync(() -> {
                AtomicReference<User<?>> user = new AtomicReference<>();
                EventSubscription<MessageEvent<UserInfoResponseMessage>> subscription = EventBus.message(UserInfoResponseMessage.class, message -> {
                    user.set(message.message().user());
                });
                subscription.close();
                return user.get();
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User<Audience> user(Audience audience) {
        return null;
    }

    @Override
    public Optional<User<Audience>> user(UUID uuid) {
        return Optional.of(users.get(uuid));
    }

    @Override
    public Optional<User<Audience>> user(String name) {
        return users.values().stream().filter(user -> user.name().equals(name)).findFirst();
    }

    @Override
    public Collection<User<Audience>> users() {
        return users.values();
    }

    @Override
    public Collection<User<Audience>> global() {
        return users.values();
    }

    @Override
    public User<Audience> deserialize(ByteBuffer buffer) {
        return new UserImpl(buffer);
    }
}
