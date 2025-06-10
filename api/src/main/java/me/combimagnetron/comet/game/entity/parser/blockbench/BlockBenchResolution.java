package me.combimagnetron.comet.game.entity.parser.blockbench;

import com.google.gson.annotations.SerializedName;

public record BlockBenchResolution(@SerializedName("width") int width, @SerializedName("height") int height) {

}