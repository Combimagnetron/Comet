package me.combimagnetron.comet;

import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.game.menu.Menu;
import me.combimagnetron.comet.game.menu.Pos2D;
import me.combimagnetron.comet.game.menu.draft.Draft;
import me.combimagnetron.comet.game.menu.element.Position;
import me.combimagnetron.comet.game.menu.element.div.Div;
import me.combimagnetron.comet.game.menu.element.impl.ButtonElement;
import me.combimagnetron.comet.image.Canvas;
import me.combimagnetron.comet.image.PixelPattern;
import me.combimagnetron.comet.internal.network.packet.server.ServerSetPlayerRotation;
import me.combimagnetron.comet.util.FontUtil;
import me.combimagnetron.comet.util.ImageProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.metadata.display.ItemDisplayMeta;
import net.minestom.server.entity.metadata.display.TextDisplayMeta;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerPacketEvent;
import net.minestom.server.event.player.PlayerPacketOutEvent;
import net.minestom.server.event.player.PlayerStartSneakingEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.network.packet.client.play.ClientPlayerRotationPacket;
import net.minestom.server.network.packet.server.play.CameraPacket;
import net.minestom.server.network.packet.server.play.EntityRotationPacket;
import net.minestom.server.network.packet.server.play.SetPassengersPacket;
import org.fit.cssbox.demo.BoxBrowser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    private final UserManager userManager;
    private final static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
    private static BufferedImage image;

    public Main() {
        MinecraftServer minecraftServer = MinecraftServer.init();
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();
        instanceContainer.setChunkSupplier(LightingChunk::new);
        MojangAuth.init();
        instanceContainer.setGenerator(unit -> unit.modifier().fillHeight(-1, 0, Block.GRASS_BLOCK));
        userManager = new UserManager();
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Pos(0, 0, 0));
            player.setGameMode(GameMode.CREATIVE);
            //SimpleCanvas canvas = SimpleCanvas.url("https://i.imgur.com/lcCkJp3.png");
            //CanvasRenderer.EntityReference entityReference = CanvasRenderer.render(canvas, userManager.user(player));
            //ChestMenu menu = ChestMenu.menu(userManager.user(player));
            MinecraftServer.LOGGER.info("test");
        });
        //boxBrowser.getBrowserCanvas().setMaximumSize(new Dimension(512, 256));
        AtomicReference<Entity> cursor = new AtomicReference<>();
        AtomicReference<Float> pitch = new AtomicReference<>(0f);
        AtomicReference<Float> yaw = new AtomicReference<>(0f);
        globalEventHandler.addListener(PlayerStartSneakingEvent.class, event -> {
            Player player = event.getPlayer();
            player.setGameMode(GameMode.SPECTATOR);

            Menu menu = null;

            Entity entity = new Entity(EntityType.TEXT_DISPLAY);
            Component part1 = render(((Menu.Impl)menu).render().sub(Pos2D.of(256, 256), Pos2D.of(0, 0)));
            Component part2 = render(((Menu.Impl)menu).render().sub(Pos2D.of(256, 256), Pos2D.of(256, 0)));
            Entity next = new Entity(EntityType.TEXT_DISPLAY);
            TextDisplayMeta nextm = (TextDisplayMeta) next.getEntityMeta();
            nextm.setText(part2);
            TextDisplayMeta meta = (TextDisplayMeta) entity.getEntityMeta();
            meta.setText(part1);
            spawnEntity(next, new Pos(15.9375, 0, 0), instanceContainer);
            spawnEntity(entity, new Pos(0, 0, 0), instanceContainer);
            Entity spectator = new Entity(EntityType.ITEM_DISPLAY);
            cursor.set(new Entity(EntityType.ITEM_DISPLAY));
            spawnEntity0(spectator, new Pos(8- 2*0.03125, 8, 12 - 2*0.03125), instanceContainer);
            spawnEntity0(cursor.get(), new Pos(8,4,0.03125), instanceContainer);
            ItemDisplayMeta cursorMeta = (ItemDisplayMeta) cursor.get().getEntityMeta();
            cursorMeta.setItemStack(ItemStack.of(Material.STONE));
            cursorMeta.setTransformationInterpolationDuration(2);
            player.sendPacket(new CameraPacket(spectator));
            player.sendPacket(new SetPassengersPacket(spectator.getEntityId(), List.of(player.getEntityId())));
        });
        globalEventHandler.addListener(PlayerPacketEvent.class, event -> {
            if (event.getPacket() instanceof ClientPlayerRotationPacket packet) {
                if (packet.yaw() == 180 && yaw.get() == 0) {
                    return;
                }
                yaw.set((float) (yaw.get() + (1.5 * (yaw.get() - packet.yaw()))));
                pitch.set((float) (pitch.get() + (1.5 * (pitch.get() - packet.pitch()))));
                if (cursor.get() != null) {
                    Pos pos = cursor.get().getPosition();
                    float x = packet.yaw();
                    float y = -packet.pitch();
                    if (yaw.get() != 0 && pitch.get() != 0 && yaw.get() != packet.yaw() * 1.5 && pitch.get() != packet.pitch() * 1.5 && (((int) (yaw.get() - packet.yaw() * 2))) != 0 && ((int) (pitch.get() - packet.pitch() * 2)) != 0) {
                        Pos teleport = new Pos(pos.x() - (x/20), pos.y() - (y/20), pos.z());
                        ItemDisplayMeta cursorMeta = (ItemDisplayMeta) cursor.get().getEntityMeta();
                        //cursorMeta.setTranslation(cursorMeta.getTranslation().sub(teleport));
                        cursor.get().teleport(cursor.get().getPosition().sub(teleport));
                        //cursor.get().teleport(cursor.get().getPosition().add(packet.yaw() - yaw.get()/250, packet.pitch() - pitch.get()/250, 0));
                    }
                }
            }
        });
        minecraftServer.start("0.0.0.0", 25565);
    }

    private Component render(Canvas canvas) {
        try {
            return executor.submit(() -> generate(canvas)).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private void spawnEntity(Entity entity, Pos pos, InstanceContainer container) {
        try {
            entity.setInstance(container, pos).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        entity.spawn();
        TextDisplayMeta meta = (TextDisplayMeta) entity.getEntityMeta();
        meta.setScale(new Vec((double) 2.5, (double) 2.5, (double) 2.5));
        meta.setLineWidth(Integer.MAX_VALUE);
        meta.setHasNoGravity(true);
        meta.setTranslation(new Pos(-0.03125, 18 * 0.03125, 0));
        entity.setNoGravity(true);
    }
    private void spawnEntity0(Entity entity, Pos pos, InstanceContainer container) {
        try {
            entity.setInstance(container, pos).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        entity.spawn();
        ItemDisplayMeta meta = (ItemDisplayMeta) entity.getEntityMeta();
        entity.setView(180f, 0f);
        meta.setHasNoGravity(true);
        entity.setNoGravity(true);
    }

    private static Component generate(Canvas canvas) {
        image = ((Canvas.InternalCanvas) canvas).image();
        long start = System.currentTimeMillis();
        int r = 3;
        System.out.println("Image: " + (System.currentTimeMillis() - start));
        TextComponent.Builder component = Component.text();
        for (int x = 0; x < image.getHeight(); x= x+3) {
            for (int y = 0; y < image.getWidth();y= y+3) {
                if (x + 3 > image.getHeight() || y + 3 > image.getWidth()) {
                    continue;
                }
                BufferedImage section = image.getSubimage(y, x, 3, 3);
                component.append(FontUtil.offset(-1), PixelPattern.optimize(section, r));
            }
            if (r == -7) {
                r = 2;
                component.append(Component.newline(), Component.newline(), Component.newline());
            } else {
                r--;
                component.append(FontUtil.offset(-(image.getWidth() - (image.getWidth() % 3))));
            }
            boolean last = image.getHeight() - x <= 3;
            if (last) {
                component.append(FontUtil.offset(2*(image.getWidth() - (image.getWidth() % 3))));
            }
        }

        System.out.println(System.currentTimeMillis() - start);
        return component.build();
    }

}
