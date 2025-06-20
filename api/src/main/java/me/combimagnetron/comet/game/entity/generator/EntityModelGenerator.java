package me.combimagnetron.comet.game.entity.generator;

import com.google.gson.Gson;
import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.game.entity.parser.blockbench.BlockBenchElement;
import me.combimagnetron.comet.game.entity.parser.blockbench.BlockBenchModel;
import me.combimagnetron.comet.game.entity.parser.blockbench.BlockBenchTexture;
import me.combimagnetron.comet.util.file.Folder;
import me.combimagnetron.comet.game.resourcepack.ResourcePackFeature;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Override;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class EntityModelGenerator implements ResourcePackFeature<EntityModelGenerator> {
    private static int modelDataCursor = 0;
    private final Map<Integer, String> textureStringMap = new HashMap<>();

    public static void generate(BlockBenchModel model) {
        new EntityModelGenerator().generateModel(model);
    }

    private void generateModel(BlockBenchModel model) {
        ASSETS_FOLDER.toFile().mkdirs();
        for (BlockBenchTexture texture : model.textures()) {
            ASSETS_FOLDER.resolve("textures/entity").toFile().mkdirs();
            String path = "comet:entity/" + model.name();
            textureStringMap.put(Integer.valueOf(texture.id()), path);
            File file = ASSETS_FOLDER.resolve("textures/entity/" + model.name() + ".png").toFile();
            try {
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(texture.source().split(",")[1])));
                ImageIO.write(image, "png", file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        Gson gson = new Gson();
        for (BlockBenchElement element : model.elements()) {
            Item item = item(element, model);
            String text = gson.toJson(item);
            File file = ASSETS_FOLDER.resolve("models/engine/" + model.name()).toFile();
            file.mkdirs();
            File writerFile = new File(file.getPath() + "/" + element.name() + ".json");
            try(FileWriter writer = new FileWriter(writerFile)) {
                writer.write(text);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }


    }

    private Item item(BlockBenchElement element, BlockBenchModel model) {
        final Item item = new Item(modelDataCursor++);
        item.element(element.cube(model.resolution()));
        item.textures().putAll(textureStringMap);
        return item;
    }

    @java.lang.Override
    public Folder folder() {
        return Folder.empty("engine");
    }

    @Override
    public Identifier identifier() {
        return null;
    }
}
