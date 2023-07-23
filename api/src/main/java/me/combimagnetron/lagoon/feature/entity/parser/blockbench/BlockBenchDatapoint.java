package me.combimagnetron.lagoon.feature.entity.parser.blockbench;

import com.google.gson.annotations.SerializedName;

public record BlockBenchDatapoint(@SerializedName("x") double x, @SerializedName("y") double y, @SerializedName("z") double z) {

}