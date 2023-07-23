package me.combimagnetron.lagoon.command.argument;

import java.util.Collection;

public interface Argument<T> {

    Class<?> type();

    T value();

    Collection<String> completions();

}
