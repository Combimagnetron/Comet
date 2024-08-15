package me.combimagnetron.comet.satellite.compiler;

public interface SatelliteField {

    String name();

    record MessageField(String name, int id, RegisteredType<?> parameters) implements SatelliteField {

    }

    record TypeField(String name, RegisteredType<?> parameters) implements SatelliteField {

    }

}
