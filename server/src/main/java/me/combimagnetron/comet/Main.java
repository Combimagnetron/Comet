package me.combimagnetron.comet;

import me.combimagnetron.comet.game.menu.CanvasRenderer;
import me.combimagnetron.comet.image.Canvas;
import me.combimagnetron.comet.image.CanvasSection;
import me.combimagnetron.comet.internal.entity.impl.display.TextDisplay;
import me.combimagnetron.comet.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.comet.internal.menu.ChestMenu;
import me.combimagnetron.comet.internal.network.packet.client.ClientSpawnEntity;
import me.combimagnetron.comet.util.FontUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.metadata.display.TextDisplayMeta;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerStartSneakingEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.block.Block;

import javax.imageio.ImageIO;
import javax.swing.plaf.TextUI;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;

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
            //Canvas canvas = Canvas.url("https://i.imgur.com/lcCkJp3.png");
            //CanvasRenderer.EntityReference entityReference = CanvasRenderer.render(canvas, userManager.user(player));
            //ChestMenu menu = ChestMenu.menu(userManager.user(player));
            MinecraftServer.LOGGER.info("test");
        });
        try {
            image = ImageIO.read(new URL("https://i.imgur.com/gxafjDo.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        globalEventHandler.addListener(PlayerStartSneakingEvent.class, event -> {
            TextDisplay display = TextDisplay.textDisplay(Vector3d.vec3(0));
            //userManager.user(player).connection().send(ClientSpawnEntity.spawnEntity(display));
            Entity entity = new Entity(EntityType.TEXT_DISPLAY);
            try {
                entity.setInstance(instanceContainer, Pos.ZERO).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            double time = System.currentTimeMillis();
            Component text;
            try {
                text = executor.submit(() -> generate("")).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Time: " + (System.currentTimeMillis() - time));
            entity.spawn();
            entity.setNoGravity(true);
            TextDisplayMeta meta = (TextDisplayMeta) entity.getEntityMeta();
            meta.setText(text);
            meta.setLineWidth(Integer.MAX_VALUE);
            meta.setHasNoGravity(true);
        });
        minecraftServer.start("0.0.0.0", 25565);
    }

    private static Component generate(String url) {
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
                component.append(FontUtil.offset(-1), CanvasSection.optimize(section, r));
            }
            if (r == -7) {
                r = 2;
                // practically the same as appendNewLine but times 3 lmao
                component.append(Component.newline(), Component.newline(), Component.newline());
            } else {
                r--;
                component.append(FontUtil.offset(-(image.getWidth() - (image.getWidth() % 3))));
            }
        }

        System.out.println(System.currentTimeMillis() - start);
        return component.build();
    }

}
