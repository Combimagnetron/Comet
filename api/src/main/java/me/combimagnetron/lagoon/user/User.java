package me.combimagnetron.lagoon.user;

import me.combimagnetron.lagoon.data.impl.UserDataContainer;
import me.combimagnetron.lagoon.instance.Instance;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

import java.util.UUID;

public interface User<T extends Audience> {

     T platformSpecificPlayer();

     void message(Component component);

     Instance instance();

     UserDataContainer playerData();

     UUID uniqueIdentifier();

}
