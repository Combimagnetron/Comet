package me.combimagnetron.lagoon.communication.message.impl.instancebound;

import me.combimagnetron.lagoon.feature.tempworld.level.GameLevelSettings;
import me.combimagnetron.lagoon.instance.Instance;
import org.jetbrains.annotations.Nullable;

public class InstanceBoundCreateGameLevelMessage extends InstanceBoundMessage {
    //private final GameLevelSettings bluePrint;

    public InstanceBoundCreateGameLevelMessage(Instance target, GameLevelSettings bluePrint) {
        super(0x02, null, target);
        //this.bluePrint = bluePrint;
        write();
    }

    public InstanceBoundCreateGameLevelMessage(byte[] bytes) {
        super(bytes);
        //this.bluePrint = (GameLevelSettings) readObject();
    }

    public GameLevelSettings bluePrint() {
        //return bluePrint;
        return null;
    }

    @Override
    public @Nullable Instance origin() {
        return null;
    }

    @Override
    public void write() {
        //writeObject(bluePrint);
    }
}
