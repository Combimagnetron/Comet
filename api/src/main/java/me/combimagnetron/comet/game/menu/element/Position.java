package me.combimagnetron.comet.game.menu.element;

public interface Position {

    double x();

    double y();

    static Position pixel(double x, double y) {
        return new PixelPosition(x, y);
    }

    static Position percentage(double x, double y) {
        return new RelativePosition(x, y);
    }

    static UnitPosition unit(double unit) {
        return new UnitPosition(unit);
    }

    record PixelPosition(double x, double y) implements Position {

    }

    final class RelativePosition implements Position {
        private final double relativeX;
        private final double relativeY;
        private int x, y;

        RelativePosition(double x, double y) {
            this.relativeX = x/100;
            this.relativeY = y/100;
        }

        public void set(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public double x() {
            return relativeX * x;
        }

        @Override
        public double y() {
            return relativeY * y;
        }
    }

    final class UnitPosition implements Position {
        private final double unit;
        private double x, y = 0;

        UnitPosition(double unit) {
            this.unit = unit;
        }

        public void calculate(int x, int y) {
            this.x = x * unit;
            this.y = y * unit;
        }

        @Override
        public double x() {
            return x;
        }

        @Override
        public double y() {
            return y;
        }
    }


}
