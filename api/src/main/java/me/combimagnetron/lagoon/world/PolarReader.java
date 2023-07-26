package me.combimagnetron.lagoon.world;

import com.github.luben.zstd.Zstd;
import me.combimagnetron.lagoon.communication.serializer.ByteBuffer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jglrxavpok.hephaistos.nbt.*;

import java.io.IOException;
import java.io.InputStream;

public class PolarReader {
    private PolarReader() {}

    public static @NotNull PolarWorld read(byte @NotNull [] data) {
        ByteBuffer buffer = new ByteBuffer();
        buffer.read(data);
        var magicNumber = buffer.readInt();
        assertThat(magicNumber == PolarWorld.MAGIC_NUMBER, "Invalid magic number");

        short version = buffer.readShort();
        validateVersion(version);

        PolarWorld.CompressionType compression = PolarWorld.CompressionType.fromId(buffer.readByte());
        assertThat(compression != null, "Invalid compression type");
        int compressedDataLength = buffer.readVarInt();

        // Replace the buffer with a "decompressed" version. This is a no-op if compression is NONE.
        buffer = decompressBuffer(buffer, compression, compressedDataLength);

        byte minSection = buffer.readByte(), maxSection = buffer.readByte();
        assertThat(minSection < maxSection, "Invalid section range");

        var chunks = buffer.readCollection(b -> readChunk(version, b, maxSection - minSection + 1));

        return new PolarWorld(version, compression, minSection, maxSection, chunks);
    }

    private static @NotNull PolarChunk readChunk(short version, ByteBuffer buffer, int sectionCount) {
        int chunkX = buffer.readVarInt();
        int chunkZ = buffer.readVarInt();

        PolarSection[] sections = new PolarSection[sectionCount];
        for (int i = 0; i < sectionCount; i++) {
            sections[i] = readSection(version, buffer);
        }

        var blockEntities = buffer.readCollection(b -> readBlockEntity(version, b));

        var heightmaps = new byte[PolarChunk.HEIGHTMAP_BYTE_SIZE][PolarChunk.HEIGHTMAPS.length];
        int heightmapMask = buffer.readInt();
        for (int i = 0; i < PolarChunk.HEIGHTMAPS.length; i++) {
            if ((heightmapMask & PolarChunk.HEIGHTMAPS[i]) == 0)
                continue;

            heightmaps[i] = buffer.readByteArray(32);
        }

        // Objects
        byte[] userData = new byte[0];
        if (version > PolarWorld.VERSION_USERDATA_OPT_BLOCK_ENT_NBT)
            userData = buffer.readByteArray();

        return new PolarChunk(
                chunkX, chunkZ,
                sections,
                blockEntities,
                heightmaps,
                userData
        );
    }

    private static @NotNull PolarSection readSection(short version, @NotNull ByteBuffer buffer) {
        // If section is empty exit immediately
        if (buffer.readBoolean()) return new PolarSection();

        var blockPalette = buffer.readCollection(ByteBuffer::readString).toArray(String[]::new);
        int[] blockData = null;
        if (blockPalette.length > 1) {
            blockData = new int[PolarSection.BLOCK_PALETTE_SIZE];

            var rawBlockData = buffer.readCollection(ByteBuffer::readLong).toArray(Long[]::new);
            var bitsPerEntry = rawBlockData.length * 64 / PolarSection.BLOCK_PALETTE_SIZE;
            PaletteUtil.unpack(blockData, rawBlockData, bitsPerEntry);
        }

        var biomePalette = buffer.readCollection(ByteBuffer::readString).toArray(String[]::new);
        int[] biomeData = null;
        if (biomePalette.length > 1) {
            biomeData = new int[PolarSection.BIOME_PALETTE_SIZE];

            var rawBiomeData = buffer.readCollection(ByteBuffer::readLong).toArray(Long[]::new);
            var bitsPerEntry = rawBiomeData.length * 64 / PolarSection.BIOME_PALETTE_SIZE;
            PaletteUtil.unpack(biomeData, rawBiomeData, bitsPerEntry);
        }

        byte[] blockLight = null, skyLight = null;

        if (version > PolarWorld.VERSION_UNIFIED_LIGHT) {
            if (buffer.readBoolean())
                blockLight = buffer.readByteArray(2048);
            if (buffer.readBoolean())
                skyLight = buffer.readByteArray(2048);
        } else if (buffer.readBoolean()) {
            blockLight = buffer.readByteArray(2048);
            skyLight = buffer.readByteArray(2048);
        }

        return new PolarSection(blockPalette, blockData, biomePalette, biomeData, blockLight, skyLight);
    }

    private static @NotNull PolarChunk.BlockEntity readBlockEntity(int version, @NotNull ByteBuffer buffer) {
        int posIndex = buffer.readInt();
        var id = buffer.readBoolean() ? buffer.readString() : null;

        NBTCompound nbt = null;
        if (version <= PolarWorld.VERSION_USERDATA_OPT_BLOCK_ENT_NBT || buffer.readBoolean())
            nbt = (NBTCompound) readNbt(buffer);

        return new PolarChunk.BlockEntity(
                ChunkUtils.blockIndexToChunkPositionX(posIndex),
                ChunkUtils.blockIndexToChunkPositionY(posIndex),
                ChunkUtils.blockIndexToChunkPositionZ(posIndex),
                id, nbt
        );
    }



    private static NBT readNbt(ByteBuffer buffer) {
        NBTReader nbtReader = null;
        nbtReader = new NBTReader(new InputStream() {
            @Override
            public int read() {
                return buffer.readByte() & 0xFF;
            }

            @Override
            public int available() {
                return -1;
            }
        }, CompressedProcesser.NONE);
        try {
            return nbtReader.read();
        } catch (IOException | NBTException e) {
            throw new RuntimeException(e);
        }
    }

    private static void validateVersion(int version) {
        var invalidVersionError = String.format("Unsupported Polar version. Up to %d is supported, found %d.",
                PolarWorld.LATEST_VERSION, version);
        assertThat(version <= PolarWorld.LATEST_VERSION, invalidVersionError);
    }

    private static @NotNull ByteBuffer decompressBuffer(@NotNull ByteBuffer buffer, @NotNull PolarWorld.CompressionType compression, int length) {
        return switch (compression) {
            case NONE -> buffer;
            case ZSTD -> {
                byte[] bytes = Zstd.decompress(buffer.toBytes(), length);
                ByteBuffer newBuffer = new ByteBuffer();
                newBuffer.read(bytes);
                yield newBuffer;
            }
        };
    }

    @Contract("false, _ -> fail")
    private static void assertThat(boolean condition, @NotNull String message) {
        if (!condition) throw new Error(message);
    }

    public static class Error extends RuntimeException {
        private Error(String message) {
            super(message);
        }
    }

}
