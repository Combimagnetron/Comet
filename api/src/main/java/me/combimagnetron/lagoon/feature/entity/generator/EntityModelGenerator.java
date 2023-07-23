package me.combimagnetron.lagoon.feature.entity.generator;

import com.google.gson.Gson;
import me.combimagnetron.lagoon.Comet;
import me.combimagnetron.lagoon.feature.entity.parser.blockbench.*;
import me.combimagnetron.lagoon.file.Folder;
import me.combimagnetron.lagoon.resourcepack.ResourcePackFeature;
import org.bukkit.Bukkit;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class EntityModelGenerator implements ResourcePackFeature<EntityModelGenerator> {
    private static int modelDataCursor = 0;
    private final Map<Integer, String> textureStringMap = new HashMap<>();

    public void generate(BlockBenchModel model) {
        ASSETS_FOLDER.toFile().mkdirs();
        for (BlockBenchTexture texture : model.textures()) {
            ASSETS_FOLDER.resolve("textures/entity").toFile().mkdirs();
            String path = "comet:entity/" + model.name();
            textureStringMap.put(Integer.valueOf(texture.id()), path);
            File file = ASSETS_FOLDER.resolve("textures/entity/" + model.name() + ".png").toFile();
            Bukkit.getLogger().info(texture.source());
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
            Bukkit.getLogger().info("daaa");
            try(FileWriter writer = new FileWriter(writerFile)) {
                writer.write(text);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
            Bukkit.getLogger().info("baaa");
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
}
