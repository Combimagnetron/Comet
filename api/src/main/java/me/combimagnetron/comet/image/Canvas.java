package me.combimagnetron.comet.image;

import me.combimagnetron.comet.concurrency.Scheduler;
import me.combimagnetron.comet.game.menu.Pos2D;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;

public class Canvas {
    private static final String NAMESPACE = "comet";
    private final BufferedImage bufferedImage;
    private final TextComponent[][] image;
    private final int width;
    private final int height;

    protected Canvas(int width, int height) {
        this.bufferedImage = new BufferedImage(width, height, 2);
        image = (TextComponent[][]) Array.newInstance(TextComponent.class, width, height);
        this.width = width;
        this.height = height;
    }

    public Canvas(int width, int height, BufferedImage image) {
        this.bufferedImage = image;
        this.image = (TextComponent[][]) Array.newInstance(TextComponent.class, height, width);
        this.width = width;
        this.height = height;
    }


    public Pos2D dimensions() {
        return Pos2D.of(width, height);
    }

    public Canvas splice(Pos2D size, Pos2D coords) {
        final BufferedImage spliced = bufferedImage.getSubimage((int)coords.x(), (int)coords.y(), (int)size.x(), (int)size.y());
        return new Canvas((int)size.x(), (int)size.y(), spliced);
    }

    public BufferedImage image() {
        return bufferedImage;
    }


    public static Canvas empty(Pos2D size) {
        return new Canvas((int) size.x(), (int) size.y());
    }

    public Component render() {
        Component component = Component.empty();
        for (int row = image.length - 1; row > 0; row--) {
            for (int col = 0; col < image[row].length; col++) {
                component = component.append(image[row][col]).append(Component.text("x")).font(Key.key(NAMESPACE + ":dynamic"));
            }
            component = component.append(Component.text("z").font(Key.key(NAMESPACE + ":dynamic")));
        }
        return component;
    }

    public Component renderAsync() {
        return Scheduler.async(this::render);
    }

    public static Canvas image(BufferedImage image) {
        Canvas canvas = new Canvas(image.getWidth(), image.getHeight(), image);
        SampleColor lastColor = SampleColor.of(0, 0, 0);
        int i = 57344;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color c = new Color(image.getRGB(x, y), true);
                TextColor color = TextColor.color(c.getRGB());
                SampleColor currentColor = SampleColor.of(color);
                if (c.getAlpha() == 0) {
                    canvas.image[x][y] = Component.text(' ');
                /*} else if (currentColor.skip(lastColor)) {
                    canvas.image[x][y] = Component.text((char) i);*/
                } else {
                    canvas.image[x][y] = Component.text((char) i, color);
                    lastColor = currentColor;
                }
                i++;
            }
            i = 57344;
        }
        return canvas;
    }

    public static Canvas url(String link) {
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new URL(link));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image(bufferedImage);
    }

    record SampleColor(int r, int g, int b) {

        public static SampleColor of(int r, int g, int b) {
            return new SampleColor(r, g, b);
        }

        public static SampleColor of(TextColor textColor) {
            return new SampleColor(textColor.red(), textColor.green(), textColor.blue());
        }

        public Color color() {
            return new Color(r, g, b);
        }

        public boolean skip(SampleColor other) {
            //return isClose(other, 0);
            if (other.r == 256 && other.g == 256 && other.b == 256) {
                return true;
            }

            return similar(color(), other.color(), 0);
        }

        private boolean isClose(SampleColor sampleColor, int threshold) {
            var external = sampleColor.color();
            var local = color();
            int r = external.getRed() - local.getRed(), g = external.getGreen() - local.getGreen(), b = external.getBlue()- local.getBlue();
            return (r*r + g*g + b*b) <= threshold*threshold;
        }

        public static boolean similar(Color color1, Color color2, int threshold) {
            int redDiff = Math.abs(color1.getRed() - color2.getRed());
            int greenDiff = Math.abs(color1.getGreen() - color2.getGreen());
            int blueDiff = Math.abs(color1.getBlue() - color2.getBlue());

            int totalDifference = redDiff + greenDiff + blueDiff;

            return totalDifference <= threshold;
        }

    }



}
