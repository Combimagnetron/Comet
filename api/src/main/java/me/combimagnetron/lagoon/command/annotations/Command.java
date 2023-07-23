package me.combimagnetron.lagoon.command.annotations;

import me.combimagnetron.lagoon.command.argument.Argument;
import me.combimagnetron.lagoon.user.User;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String command();

}
