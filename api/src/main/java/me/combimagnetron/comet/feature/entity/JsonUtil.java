package me.combimagnetron.comet.feature.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.LinkedHashSet;
import java.util.Set;

public class JsonUtil {

    public static int[] readIntArray(JsonObject object, String name, int size) {
        if (object == null) {
            return new int[size];
        }
        JsonArray array = object.get(name).getAsJsonArray();
        if (array == null) {
            return new int[size];
        }
        Set<Integer> tempStorage = new LinkedHashSet<>();
        for (JsonElement element : array) {
            tempStorage.add(element.getAsInt());
        }
        return tempStorage.stream().mapToInt(Number::intValue).toArray();
    }
}
