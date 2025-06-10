package me.combimagnetron.comet.game.entity.model;

import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.game.entity.parser.blockbench.BlockBenchModel;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public record ModelTemplate(Identifier identifier, BlockBenchModel blockBenchModel) {

    public static ModelTemplate of(Identifier identifier, BlockBenchModel blockBenchModel) {
        return new ModelTemplate(identifier, blockBenchModel);
    }

}
