package me.combimagnetron.lagoon.command.argument.impl;

import me.combimagnetron.lagoon.Comet;
import me.combimagnetron.lagoon.command.argument.Argument;
import me.combimagnetron.lagoon.user.User;

import java.util.Collection;

public class UserArgument implements Argument<User<?>> {

    @Override
    public Class<?> type() {
        return User.class;
    }

    @Override
    public User<?> value() {
        return null;
    }

    @Override
    public Collection<String> completions() {
        return null;
    }

    public static UserArgument from(String string) {
        return new UserArgument();
    }

}
