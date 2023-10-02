package me.combimagnetron.comet.feature.entity.parser.blockbench;

import com.google.gson.annotations.SerializedName;

public record BlockBenchMeta(@SerializedName("version") String version, @SerializedName("model_format") String modelFormat, @SerializedName("box_uv") boolean boxUv) {

}