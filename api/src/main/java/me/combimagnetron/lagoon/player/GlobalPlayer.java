package me.combimagnetron.lagoon.player;

import me.combimagnetron.lagoon.data.impl.UserDataContainer;
import me.combimagnetron.lagoon.instance.Instance;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

import java.util.UUID;

public interface GlobalPlayer<T extends Audience> {

     T platformSpecificPlayer();

     void sendMessage(Component component);

     Instance instance();

     UserDataContainer playerData();

     UUID uniqueIdentifier();

}
