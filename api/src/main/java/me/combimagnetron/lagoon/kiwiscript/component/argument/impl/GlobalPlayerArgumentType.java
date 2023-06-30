package me.combimagnetron.lagoon.kiwiscript.component.argument.impl;

import me.combimagnetron.lagoon.kiwiscript.component.argument.ArgumentType;
import me.combimagnetron.lagoon.player.GlobalPlayer;

public class GlobalPlayerArgumentType extends ArgumentType<GlobalPlayer> {
    public GlobalPlayerArgumentType(String name) {
        super(name, GlobalPlayer.class);
    }
}