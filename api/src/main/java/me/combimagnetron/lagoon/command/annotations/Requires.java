package me.combimagnetron.lagoon.command.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Requires {

    String permission() default "";

    String condition() default "";

}
