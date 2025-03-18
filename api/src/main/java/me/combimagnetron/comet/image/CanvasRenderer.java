package me.combimagnetron.comet.image;

import me.combimagnetron.comet.util.FontUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

import java.awt.image.BufferedImage;

public interface CanvasRenderer {
    OptimizedCanvasRenderer OPTIMIZED = new OptimizedCanvasRenderer();

    Frame render(Canvas canvas);

    record Frame(Component component, int width, int height) {

        public static Frame wrap(Component component, int width, int height) {
            return new Frame(component, width, height);
        }

    }

    static CanvasRenderer optimized() {
        return OPTIMIZED;
    }

    class OptimizedCanvasRenderer implements CanvasRenderer {

        @Override
        public Frame render(Canvas canvas) {
            Canvas.InternalCanvas internalCanvas = (Canvas.InternalCanvas) canvas;
            BufferedImage image = internalCanvas.image();
            int r = 3;
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
            }
            Component finished = component.build();
            return Frame.wrap(finished, image.getWidth(), image.getHeight());
        }
    }

}
