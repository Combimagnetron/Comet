package me.combimagnetron.comet.user;

import me.combimagnetron.comet.communication.message.user.UserMessageChannel;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.instance.Instance;
import me.combimagnetron.comet.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.comet.internal.network.ByteBuffer;
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

     UserMessageChannel messageChannel();

     Instance instance();

     UserDataContainer playerData();

     UUID uniqueIdentifier();

     Connection connection();

     Vector3d position();

     Identifier location();

     me.combimagnetron.generated.baseservice.User sat();

     /*
     1. UUID -> Unique Identifier
     2. String -> Name
     3. UUId -> Instance
     4. Identifier -> Message Channel
     5. DataContainer -> Player Data
     6. 3x Double -> Vector3d Position
     7. Identifier -> Location
      */
     default ByteBuffer serialize() {
          ByteBuffer buffer = ByteBuffer.empty();
          buffer.write(ByteBuffer.Adapter.UUID, uniqueIdentifier());
          buffer.write(ByteBuffer.Adapter.STRING, name());
          buffer.write(ByteBuffer.Adapter.UUID, instance().uniqueIdentifier());
          buffer.write(ByteBuffer.Adapter.IDENTIFIER, messageChannel().identifier());
          buffer.write(ByteBuffer.Adapter.DATA_CONTAINER, playerData());
          buffer.write(ByteBuffer.Adapter.DOUBLE, position().x()).write(ByteBuffer.Adapter.DOUBLE, position().y()).write(ByteBuffer.Adapter.DOUBLE, position().z());
          buffer.write(ByteBuffer.Adapter.IDENTIFIER, location());
          return buffer;
     }

}
