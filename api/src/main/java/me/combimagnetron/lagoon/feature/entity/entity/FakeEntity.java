package me.combimagnetron.lagoon.feature.entity.entity;


import me.combimagnetron.lagoon.feature.entity.math.Point;
import me.combimagnetron.lagoon.operation.Operation;
import me.combimagnetron.lagoon.player.GlobalPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;


import java.util.Collection;

public interface FakeEntity {

    Operation<Boolean> show(Collection<GlobalPlayer<?>> players);

    Operation<Boolean> hide(Collection<GlobalPlayer<?>> players);

    Operation<Void> headItem(ItemStack stack);

    Operation<Void> teleport(Point point);

    Operation<Void> rotateHead(EulerAngle angle);

    Operation<Collection<GlobalPlayer<?>>> viewers();

    Entity bukkitEntity();

}
