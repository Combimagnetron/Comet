package me.combimagnetron.comet.internal.block;

import me.combimagnetron.comet.data.Identifier;

public interface CustomBlock extends Block {

    Identifier identifier();

    class NoteBlock implements CustomBlock {
        private final Identifier identifier;

        protected NoteBlock(Identifier identifier) {
            this.identifier = identifier;
        }

        @Override
        public Identifier identifier() {
            return identifier;
        }

        record Instrument(int id, String name) {
        }

        record NoteBlockData(Instrument instrument, int note) {
        }

    }

    class StringBlock implements CustomBlock {
        private final Identifier identifier;

        protected StringBlock(Identifier identifier) {
            this.identifier = identifier;
        }

        @Override
        public Identifier identifier() {
            return identifier;
        }
    }

    class RedstoneDustBlock implements CustomBlock {
        private final Identifier identifier;

        protected RedstoneDustBlock(Identifier identifier) {
            this.identifier = identifier;
        }

        @Override
        public Identifier identifier() {
            return identifier;
        }
    }

}
