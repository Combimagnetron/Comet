package me.combimagnetron.comet.game.entity.parser.blockbench;

import com.google.gson.annotations.SerializedName;

public record BlockBenchFaces(@SerializedName("north") BlockBenchFace north, @SerializedName("east") BlockBenchFace east, @SerializedName("south") BlockBenchFace south,
                              @SerializedName("west") BlockBenchFace west, @SerializedName("up") BlockBenchFace up, @SerializedName("down") BlockBenchFace down) {
}