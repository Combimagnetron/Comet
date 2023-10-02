package me.combimagnetron.comet.feature.entity.parser.blockbench;

import com.google.gson.annotations.SerializedName;

public record BlockBenchFace(@SerializedName("uv") double[] uv, @SerializedName("texture") int texture, @SerializedName("rotation") int rotation) {

}