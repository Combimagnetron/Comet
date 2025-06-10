package me.combimagnetron.comet.service;

import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AutoService {
    String name();

    @Nullable Class<?>[] parameters() default {};
}
