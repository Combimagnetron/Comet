package me.combimagnetron.lagoon.image;

import me.combimagnetron.lagoon.feature.menu.menu.Pos2D;
import me.combimagnetron.lagoon.operation.Operation;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;

public class PixelRaster {
    private final BufferedImage bufferedImage;
    private final TextComponent[][] image;
    private final int width;
    private final int height;

    protected PixelRaster(int width, int height) {
        this.bufferedImage = new BufferedImage(width, height, 2);
        image = (TextComponent[][]) Array.newInstance(TextComponent.class, height, width);
        this.width = width;
        this.height = height;
    }

    public PixelRaster(int width, int height, BufferedImage image) {
        this.bufferedImage = image;
        this.image = (TextComponent[][]) Array.newInstance(TextComponent.class, height, width);
        this.width = width;
        this.height = height;
    }


    public Pos2D dimensions() {
        return Pos2D.of(width, height);
    }

    public PixelRaster splice(Pos2D size, Pos2D coords) {
        final BufferedImage spliced = bufferedImage.getSubimage((int)coords.x(), (int)coords.y(), (int)size.x(), (int)size.y());
        return new PixelRaster((int)size.x(), (int)size.y(), spliced);
    }

    public BufferedImage image() {
        return bufferedImage;
    }


    public static PixelRaster empty(Pos2D size) {
        return new PixelRaster((int) size.x(), (int) size.y());
    }

    public Component render() {
        Component component = Component.empty();
        for (int row = 0; row < image.length; row++) {
            for (int col = 0; col < image[row].length; col++) {
                component = component.append(image[row][col]).append(Component.text("x")).font(Key.key("blockplaza:dynamic"));
            }
            component = component.append(Component.text("z").font(Key.key("blockplaza:dynamic")));
        }
        return component;
    }

    public static Operation<PixelRaster> fromImage(BufferedImage image) {
        return Operation.executable(() -> {
            PixelRaster raster = new PixelRaster(image.getHeight(), image.getWidth(), image);
            int i = 57344;
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    raster.image[x][y] = Component.text((char) i, TextColor.color(image.getRGB(x, y)));
                    i++;
                }
            }
            return raster;
        });
    }

    public static Operation<PixelRaster> fromImageLink(String link) {
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new URL(link));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fromImage(bufferedImage);
    }



}
