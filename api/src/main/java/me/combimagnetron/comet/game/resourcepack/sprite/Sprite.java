package me.combimagnetron.comet.game.resourcepack.sprite;

import me.combimagnetron.comet.game.resourcepack.ResourcePackPath;
import me.combimagnetron.comet.image.SimpleCanvas;
import me.combimagnetron.comet.util.Pair;
import me.combimagnetron.comet.data.Identifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

public interface Sprite {

    ResourcePackPath path();

    SimpleCanvas raster();

    /**
     * @return Pair with the height and ascent, the shift value and null or null.
     */

    Identifier identifier();

    static Builder sprite() {
        return new Builder();
    }

    class Builder {
        private Identifier identifier;
        private Source source = Source.FILE;
        private URL url;
        private Path location;
        private int height;

        public Builder identifier(Identifier identifier) {
            if (!check(this.identifier))
                return this;
            this.identifier = identifier;
            return this;
        }


        public Builder source(Source source) {
            this.source = source;
            return this;
        }

        public Builder location(Path path) {
            if (this.source != Source.FILE)
                return this;
            this.location = path;
            return this;
        }

        public Builder url(String url) {
            if (this.source != Source.URL)
                return this;
            try {
                this.url = new URL(url);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Sprite build() {
            if (this.source == Source.URL && this.url == null || this.source == Source.FILE && this.location == null)
                throw new IllegalArgumentException();
            BufferedImage image = switch (source) {
                case URL -> {
                    try {
                        yield ImageIO.read(url);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case FILE -> {
                    try {
                        yield ImageIO.read(location.toFile());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            return new Sprite() {
                @Override
                public ResourcePackPath path() {
                    return null;
                }

                @Override
                public SimpleCanvas raster() {
                    return new SimpleCanvas(image.getWidth() , height, image);
                }

                @Override
                public Identifier identifier() {
                    return identifier;
                }
            };
        }

        private boolean check(Object object) {
            return object == null;
        }

        public enum Type {
            BITMAP_SINGLE, BITMAP_SHEET
        }

        public enum Source {
            URL, FILE
        }
    }


}
