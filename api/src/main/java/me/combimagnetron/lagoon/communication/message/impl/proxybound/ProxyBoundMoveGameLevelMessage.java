package me.combimagnetron.lagoon.communication.message.impl.proxybound;

import com.google.gson.JsonObject;
import me.combimagnetron.lagoon.feature.tempworld.level.GameLevel;
import me.combimagnetron.lagoon.instance.Instance;
import me.combimagnetron.lagoon.internal.network.ByteBuffer;
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
        write(ByteBuffer.Adapter.STRING, object.getAsString());
    }
}
