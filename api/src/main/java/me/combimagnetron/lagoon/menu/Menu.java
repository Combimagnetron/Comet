package me.combimagnetron.lagoon.menu;

import me.combimagnetron.lagoon.operation.Operation;
import org.jetbrains.annotations.Nullable;

public interface Menu {

    @Nullable Operation<Void> setItem(int slot);

    enum Type {
        CHEST, ADVANCED
    }
}
