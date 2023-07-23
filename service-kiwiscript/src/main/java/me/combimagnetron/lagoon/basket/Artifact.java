package me.combimagnetron.lagoon.basket;

public interface Artifact {

    class Block implements Artifact {
        private final boolean blueprint = false;
    }

    class Function implements Artifact {

    }

    class Condition implements Artifact {

    }

}
