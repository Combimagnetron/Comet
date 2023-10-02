package me.combimagnetron.comet.feature.entity.entity;


import me.combimagnetron.comet.feature.entity.math.Point;
import me.combimagnetron.comet.operation.Operation;
import me.combimagnetron.comet.user.User;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;


import java.util.Collection;

public interface FakeEntity {

    Operation<Boolean> show(Collection<User<?>> players);

    Operation<Boolean> hide(Collection<User<?>> players);

    Operation<Void> headItem(ItemStack stack);

    Operation<Void> teleport(Point point);

    Operation<Void> rotateHead(EulerAngle angle);

    Operation<Collection<User<?>>> viewers();

    Entity bukkitEntity();

}
