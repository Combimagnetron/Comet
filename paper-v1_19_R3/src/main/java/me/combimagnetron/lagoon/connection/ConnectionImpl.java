package me.combimagnetron.lagoon.connection;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.papermc.paper.network.ChannelInitializeListener;
import io.papermc.paper.network.ChannelInitializeListenerHolder;
import me.combimagnetron.lagoon.CometBase;
import me.combimagnetron.lagoon.internal.Item;
import me.combimagnetron.lagoon.internal.network.ByteBuffer;
import me.combimagnetron.lagoon.internal.network.VersionRegistry;
import me.combimagnetron.lagoon.internal.network.packet.Packet;
import me.combimagnetron.lagoon.internal.network.packet.ServerPacket;
import me.combimagnetron.lagoon.internal.network.packet.client.ClientBundleDelimiter;
import me.combimagnetron.lagoon.internal.network.packet.client.ClientOpenScreen;
import me.combimagnetron.lagoon.internal.network.packet.client.ClientSetScreenContent;
import me.combimagnetron.lagoon.internal.network.packet.client.ClientSetScreenSlot;
import me.combimagnetron.lagoon.user.User;
import me.combimagnetron.lagoon.util.Values;
import net.kyori.adventure.key.Key;
import net.minecraft.core.NonNullList;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;

public class ConnectionImpl implements me.combimagnetron.lagoon.internal.network.Connection {
    private final Player player;
    private final CometBase<JavaPlugin> library;
    private ChannelPipeline channelPipeline;

    public static ConnectionImpl of(User<Player> user, CometBase<JavaPlugin> library) {
        return new ConnectionImpl(user, library);
    }

    protected ConnectionImpl(User<Player> user, CometBase<JavaPlugin> library) {
        this.player = user.platformSpecificPlayer();
        this.library = library;
        inject();
    }

    private void inject() {
        ServerGamePacketListenerImpl serverGamePacketListener = ((CraftPlayer) player).getHandle().connection;
        Connection connection;
        try {
            Field field = serverGamePacketListener.getClass().getDeclaredField("h");
            field.setAccessible(true);
            connection = (Connection) field.get(serverGamePacketListener);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        this.channelPipeline = connection.channel.pipeline().addLast(new ChannelInjector(library));
    }

    @Override
    public void send(Packet packetContainer) {
        channelPipeline.write(Transformer.findAndTransform(packetContainer));
    }

    protected static class ChannelInjector extends ChannelDuplexHandler {
        private final CometBase<JavaPlugin> library;

        protected ChannelInjector(CometBase<JavaPlugin> library) {
            this.library = library;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, @NotNull Object message) throws Exception {
            ByteBuffer buffer = ByteBuffer.of(ctx.alloc().buffer().array());
            int id = buffer.read(ByteBuffer.Adapter.INT);
            Class<? extends Packet> clazz = VersionRegistry.server(id);
            if (clazz == null) {
                super.channelRead(ctx, message);
                return;
            }
            Packet packetContainer = (Packet) clazz.getDeclaredMethod("from", ByteBuffer.class).invoke(null, buffer);
            library.network().sniffer().call(packetContainer);
            super.channelRead(ctx, message);
        }

    }

    private interface Transformer<T extends Packet> {
        Transformer<ClientOpenScreen> CLIENT_OPEN_SCREEN = Transformer.of(ClientOpenScreen.class, container -> new ClientboundOpenScreenPacket(container.windowId(), TypeTransformer.INTEGER_MENU_TYPE.transform(container.windowType()), TypeTransformer.COMPONENT_COMPONENT.transform(container.title())));
        Transformer<ClientSetScreenContent> CLIENT_SET_SCREEN_CONTENT = Transformer.of(ClientSetScreenContent.class, container -> new ClientboundContainerSetContentPacket(container.windowId(), container.stateId(), NonNullList.of(TypeTransformer.ITEM_ITEM_STACK.transform(Item.empty()), container.items().stream().map(TypeTransformer.ITEM_ITEM_STACK::transform).toList().toArray(new ItemStack[0])), TypeTransformer.ITEM_ITEM_STACK.transform(container.carried())));
        Transformer<ClientBundleDelimiter> CLIENT_BUNDLE_DELIMITER = Transformer.of(ClientBundleDelimiter.class, container -> new ClientboundBundlePacket(container.containers().stream().map(Transformer::findAndTransform).toList()));
        Transformer<ClientSetScreenSlot> CLIENT_SET_SCREEN_SLOT = Transformer.of(ClientSetScreenSlot.class, container -> new ClientboundContainerSetSlotPacket(container.windowId(), container.stateId(), container.slot(), TypeTransformer.ITEM_ITEM_STACK.transform(container.item())));

        Values<Transformer> VALUES = Values.of(CLIENT_OPEN_SCREEN, CLIENT_SET_SCREEN_CONTENT, CLIENT_BUNDLE_DELIMITER, CLIENT_SET_SCREEN_SLOT);

        Function<T, net.minecraft.network.protocol.Packet<ClientGamePacketListener>> transformer();

        Class<T> clazz();

        default net.minecraft.network.protocol.Packet<ClientGamePacketListener> transform(T t) {
            return transformer().apply(t);
        }

        static net.minecraft.network.protocol.Packet<ClientGamePacketListener> findAndTransform(Packet container) {
            return VALUES.values().stream().filter(transformer -> transformer.clazz() == container.getClass()).findAny().orElseThrow().transform(container);
        }

        static <T extends Packet> Transformer<T> of(Class<T> clazz, Function<T, net.minecraft.network.protocol.Packet<ClientGamePacketListener>> transformer) {
            return new Impl<>(clazz, transformer);
        }

        record Impl<T extends Packet>(Class<T> clazz, Function<T, net.minecraft.network.protocol.Packet<ClientGamePacketListener>> transformer) implements Transformer<T> {

        }

    }

    private static class Decoder extends ByteToMessageDecoder {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            final byte[] bytes = in.array();
            final ByteBuffer byteBuffer = ByteBuffer.of(bytes);
            final Class<? extends ServerPacket> clazz = VersionRegistry.server(byteBuffer.read(ByteBuffer.Adapter.VAR_INT));
            ServerPacket packet = (ServerPacket) clazz.getDeclaredMethod("from", ByteBuffer.class).invoke(null, byteBuffer);
            out.add(packet);
        }
    }

}
