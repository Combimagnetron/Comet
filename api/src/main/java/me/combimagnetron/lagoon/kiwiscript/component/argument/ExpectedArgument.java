package me.combimagnetron.lagoon.kiwiscript.component.argument;

import java.util.*;
import java.util.stream.Collectors;

public class ExpectedArgument {
    private final ArgumentType<?>[] argumentTypes;

    private ExpectedArgument(ArgumentType<?>... argumentTypes) {
        this.argumentTypes = argumentTypes;
    }

    public Result compare(Argument argument) {
        Map<ArgumentType<?>, Boolean> results = new HashMap<>();
        List<ArgumentType<?>> expectedTypes = Arrays.stream(argumentTypes).toList();
        List<ArgumentType<?>> usedTypes = Arrays.stream(argument.argumentTypes()).toList();
        usedTypes.forEach(type -> results.put(type, expectedTypes.contains(type)));
        return new Result(results);
    }

    public static class Result {
        private final Map<ArgumentType<?>, Boolean> results = new HashMap<>();

        private Result(Map<ArgumentType<?>, Boolean> results) {
            this.results.putAll(results);
        }

        public boolean passed() {
            return !results.containsValue(false);
        }

        public boolean result(ArgumentType<?> argumentType) {
            return results.get(argumentType);
        }

        public Collection<? extends ArgumentType<?>> passedArgumentTypes() {
            return results.entrySet().stream().filter(Map.Entry::getValue).collect(Collectors.toMap(Map.Entry::getKey, argumentTypeBooleanEntry -> true)).keySet();
        }

        public Collection<? extends ArgumentType<?>> failedArgumentTypes() {
            return results.entrySet().stream().filter(e -> !e.getValue()).collect(Collectors.toMap(Map.Entry::getKey, argumentTypeBooleanEntry -> false)).keySet();
        }


    }

}
