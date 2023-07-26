package me.combimagnetron.lagoon.command.annotations;

import me.combimagnetron.lagoon.command.argument.Argument;
import me.combimagnetron.lagoon.user.User;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Command {
    String command();

}
