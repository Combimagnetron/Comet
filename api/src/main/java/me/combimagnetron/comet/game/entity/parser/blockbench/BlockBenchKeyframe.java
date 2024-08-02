package me.combimagnetron.comet.game.entity.parser.blockbench;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public record BlockBenchKeyframe(@SerializedName("channel") String channel, @SerializedName("data_points") BlockBenchDatapoint[] datapoints, @SerializedName("uuid") UUID uuid,
                                 @SerializedName("time") double time, @SerializedName("color") int color, @SerializedName("interpolation") String interpolation) {
}
