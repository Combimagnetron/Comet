package me.combimagnetron.lagoon.world;

public class ChunkUtils {

    public static int blockIndexToChunkPositionX(int index) {
        return index & 0xF;
    }

    public static int blockIndexToChunkPositionY(int index) {
        int y = (index & 0x07FFFFF0) >>> 4;
        if (((index >>> 27) & 1) == 1) y = -y;
        return y;
    }

    public static int blockIndexToChunkPositionZ(int index) {
        return (index >> 28) & 0xF; // 28-32 bits
    }

    public static long getChunkIndex(int chunkX, int chunkZ) {
        return (((long) chunkX) << 32) | (chunkZ & 0xffffffffL);
    }

    public static int getBlockIndex(int x, int y, int z) {
        x = x % 16;
        z = z % 16;

        int index = x & 0xF; // 4 bits
        if (y > 0) {
            index |= (y << 4) & 0x07FFFFF0; // 23 bits (24th bit is always 0 because y is positive)
        } else {
            index |= ((-y) << 4) & 0x7FFFFF0; // Make positive and use 23 bits
            index |= 1 << 27; // Set negative sign at 24th bit
        }
        index |= (z << 28) & 0xF0000000; // 4 bits
        return index;
    }

}
