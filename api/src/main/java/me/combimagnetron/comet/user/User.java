package me.combimagnetron.comet.user;

import me.combimagnetron.comet.instance.Instance;
import me.combimagnetron.comet.internal.network.Connection;
import me.combimagnetron.comet.data.impl.UserDataContainer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

import java.util.UUID;

public interface User<T extends Audience> {

     T platformSpecificPlayer();

     String name();

     default void message(Component component) {
          platformSpecificPlayer().sendMessage(component);
     }

     Instance instance();

     UserDataContainer playerData();

     UUID uniqueIdentifier();

     Connection connection();

}
