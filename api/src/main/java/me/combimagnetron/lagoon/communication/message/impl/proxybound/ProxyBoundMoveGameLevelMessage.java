package me.combimagnetron.lagoon.communication.message.impl.proxybound;

import com.google.gson.JsonObject;
import me.combimagnetron.lagoon.communication.Message;
import me.combimagnetron.lagoon.game.level.GameLevel;
import me.combimagnetron.lagoon.instance.Instance;
import org.jetbrains.annotations.Nullable;

public class ProxyBoundMoveGameLevelMessage extends ProxyBoundMessage {
    private final GameLevel gameLevel;

    public ProxyBoundMoveGameLevelMessage(Instance target, GameLevel gameLevel) {
        super(0x01, null, target);
        this.gameLevel = gameLevel;
        write();
    }

    public ProxyBoundMoveGameLevelMessage(byte[] bytes) {
        super(bytes);
        this.gameLevel = null;
    }

    @Override
    public @Nullable Instance origin() {
        return null;
    }

    @Override
    public void write() {
        JsonObject object = gameLevel.serialize();
        writeString(object.getAsString());
    }
}
