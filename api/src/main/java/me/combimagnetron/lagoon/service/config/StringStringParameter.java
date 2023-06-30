package me.combimagnetron.lagoon.service.config;

import org.jetbrains.annotations.Nullable;

import java.io.File;

public class StringStringParameter extends Parameter<String, String> {
    private String first, second;

    @Override
    String first() {
        return first;
    }

    @Override
    String second() {
        return second;
    }

    @Override
    String first(String s) {
        return this.first = s;
    }

    @Override
    String second(String s) {
        return this.second = s;
    }

    @Override
    @Nullable File location() {
        return null;
    }
}
