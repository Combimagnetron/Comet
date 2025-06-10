package me.combimagnetron.comet.image;

import me.combimagnetron.comet.game.menu.Pos2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface Canvas {

    Canvas sub(Pos2D size, Pos2D coords);

    Canvas pixel(Pixel pixel);

    Canvas place(Canvas canvas, Pos2D coords);

    Canvas fill(Pos2D coords, Pos2D size, Color color);

    Pos2D size();

    Pixel pixel(Pos2D coords);

    boolean animated();

    static Canvas image(BufferedImage image) {
        return new StaticImpl(image);
    }

    static Canvas image(Pos2D size) {
        return new StaticImpl(new BufferedImage(size.xi(), size.yi(), 2));
    }

    interface InternalCanvas extends Canvas {
        BufferedImage image();
    }

    class StaticImpl implements InternalCanvas {
        private final BufferedImage image;

        protected StaticImpl(BufferedImage image) {
            this.image = image;
        }

        @Override
        public Canvas sub(Pos2D size, Pos2D coords) {
            return new StaticImpl(image.getSubimage(coords.xi(), coords.yi(), size.xi(), size.yi()));
        }

        public BufferedImage image() {
            return image;
        }

        @Override
        public Canvas pixel(Pixel pixel) {
            image.setRGB(pixel.x(), pixel.y(), pixel.color().rgb());
            return new StaticImpl(image);
        }

        @Override
        public Canvas place(Canvas canvas, Pos2D coords) {
            BufferedImage place = ((InternalCanvas) canvas).image();
            Graphics2D graphics = image.createGraphics();
            graphics.drawImage(place, coords.xi(), coords.yi(), null);
            graphics.dispose();
            return new StaticImpl(image);
        }

        @Override
        public Canvas fill(Pos2D coords, Pos2D size, Color color) {
            Graphics2D graphics = image.createGraphics();
            graphics.setColor(new java.awt.Color(color.rgb()));
            graphics.fillRect(coords.xi(), coords.yi(), size.xi(), size.yi());
            graphics.dispose();
            return new StaticImpl(image);
        }

        @Override
        public Pos2D size() {
            return Pos2D.of(image.getWidth(), image.getHeight());
        }

        @Override
        public Pixel pixel(Pos2D coords) {
            return Pixel.of(coords, Color.of(image.getRGB(coords.xi(), coords.yi())));
        }

        @Override
        public boolean animated() {
            return false;
        }
    }

}
