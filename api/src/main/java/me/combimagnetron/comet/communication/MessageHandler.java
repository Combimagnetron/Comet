package me.combimagnetron.comet.communication;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MessageHandler {
    @NotNull Class<? extends Message>[] filter() default Message.class;

}
