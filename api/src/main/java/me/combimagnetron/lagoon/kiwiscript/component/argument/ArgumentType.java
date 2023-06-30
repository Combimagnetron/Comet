package me.combimagnetron.lagoon.kiwiscript.component.argument;

import me.combimagnetron.lagoon.kiwiscript.component.argument.impl.GlobalPlayerArgumentType;
import me.combimagnetron.lagoon.kiwiscript.component.argument.impl.NumberArgumentType;
import me.combimagnetron.lagoon.kiwiscript.component.argument.impl.TextArgumentType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ArgumentType<T> {
    static final HashMap<String[], Class<? extends ArgumentType<?>>> ARGUMENT_TYPE_MAP = new HashMap<>();

    static {
        ARGUMENT_TYPE_MAP.put(new String[]{"text"}, TextArgumentType.class);
        ARGUMENT_TYPE_MAP.put(new String[]{"num", "number"}, NumberArgumentType.class);
        ARGUMENT_TYPE_MAP.put(new String[]{"player", "globalplayer", "global_player"}, GlobalPlayerArgumentType.class);
    }

    public static Class<? extends ArgumentType<?>> get(String name) {
        for (Map.Entry<String[], Class<? extends ArgumentType<?>>> entry : ARGUMENT_TYPE_MAP.entrySet()) {
            if (Arrays.asList(entry.getKey()).contains(name)) {
                return entry.getValue();
            }
        }
        return null;
    }

    private final String name;
    private final Class<T> type;

    public ArgumentType(String name, Class<T> type) {
        this.name = name;
        this.type = type;
    }

    public Class<T> classType() {
        return type;
    }

    public String name() {
        return name;
    }

}
