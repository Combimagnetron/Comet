package me.combimagnetron.comet.feature.menu;

import me.combimagnetron.comet.operation.Operation;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface Menu {

    @Nullable Operation<Void> item(int slot, ItemStack itemStack);

    enum Type {
        CHEST, ADVANCED
    }
}