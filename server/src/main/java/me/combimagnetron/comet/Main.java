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
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.metadata.display.TextDisplayMeta;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
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

public class Main {
    private final UserManager userManager;

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
            TextDisplay display = TextDisplay.textDisplay(Vector3d.vec3(0));
            //userManager.user(player).connection().send(ClientSpawnEntity.spawnEntity(display));
            Entity entity = new Entity(EntityType.TEXT_DISPLAY);
            try {
                entity.setInstance(instanceContainer, Pos.ZERO).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            BufferedImage image;
            try {
                image = ImageIO.read(new URL("https://i.imgur.com/v4DsJIv.png"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Component component = Component.empty();
            for (int x = 0; x < image.getHeight(); x= x+3) {
                for (int y = 0; y < image.getWidth();y= y+3) {
                    if (x + 3 >= image.getHeight() || y + 3 >= image.getWidth()) {
                        continue;
                    }
                    BufferedImage section = image.getSubimage(y, x, 3, 3);
                    component = component.append(FontUtil.offset(-1)).append(CanvasSection.optimize(section));
                }
                component = component.append(Component.newline());
            }
            entity.spawn();
            entity.setNoGravity(true);
            TextDisplayMeta meta = (TextDisplayMeta) entity.getEntityMeta();
            meta.setText(component);
            //meta.setLineWidth(image.getWidth());
            meta.setHasNoGravity(true);
            System.out.println("dood");
            MinecraftServer.LOGGER.info("test");
        });
        minecraftServer.start("0.0.0.0", 25565);
    }

}
