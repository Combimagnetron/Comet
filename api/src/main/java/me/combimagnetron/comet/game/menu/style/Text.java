package me.combimagnetron.comet.game.menu.style;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

public record Text(String content, Font font) {

    public static Text text(String content, Font font) {
        return new Text(content, font);
    }

    public static Text text(String content) {
        return new Text(content, Font.vanilla());
    }

    public record Font(java.awt.Font internal, int size) {
        static final Font VANILLA = new Font(Registry.VANILLA, 7);

        public static Font vanilla() {
            return VANILLA;
        }

        private final static class Registry {
            private static final java.awt.Font VANILLA;

            static {
                try {
                    URL resource = Font.class.getClassLoader().getResource("vanilla.ttf");
                    File projectPath = new File(resource.toURI());
                    VANILLA = java.awt.Font.createFont(0, projectPath);
                } catch (FontFormatException | IOException e) {
                    throw new RuntimeException(e);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }

        }

    }

}
