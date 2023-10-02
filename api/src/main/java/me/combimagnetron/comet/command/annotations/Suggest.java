package me.combimagnetron.comet.command.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Suggest {

    String[] suggestion() default {};

    String registryEntry();

}
