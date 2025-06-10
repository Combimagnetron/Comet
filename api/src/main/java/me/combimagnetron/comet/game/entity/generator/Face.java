package me.combimagnetron.comet.game.entity.generator;

import me.combimagnetron.comet.game.entity.parser.blockbench.BlockBenchFace;
import me.combimagnetron.comet.game.entity.parser.blockbench.BlockBenchResolution;

public class Face {
    private final double[] uv;
    private final int rotation;
    private final String texture;
    private final int tintIndex = 0;

    public Face(BlockBenchFace face, BlockBenchResolution resolution) {
        this.uv = new double[4];
        float ratio = ((float)resolution.width() / 16);
        uv[0] = face.uv()[0] / ratio;
        uv[1] = face.uv()[1] / ratio;
        uv[2] = face.uv()[2] / ratio;
        uv[3] = face.uv()[3] / ratio;
        this.rotation = face.rotation();
        this.texture = "#" + face.texture();
    }

    public double[] uv() {
        return uv;
    }

    public int tintIndex() {
        return tintIndex;
    }

    public int rotation() {
        return rotation;
    }

}
