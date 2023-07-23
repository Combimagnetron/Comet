package me.combimagnetron.lagoon.kiwiscript.component.argument.impl;

import me.combimagnetron.lagoon.kiwiscript.component.argument.ArgumentType;
import me.combimagnetron.lagoon.user.User;

public class GlobalPlayerArgumentType extends ArgumentType<User> {
    public GlobalPlayerArgumentType(String name) {
        super(name, User.class);
    }
}