package me.combimagnetron.lagoon.world;

import com.github.luben.zstd.Zstd;
import me.combimagnetron.lagoon.communication.serializer.ByteBuffer;
import org.jetbrains.annotations.NotNull;
import org.jglrxavpok.hephaistos.nbt.CompressedProcesser;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;
import org.jglrxavpok.hephaistos.nbt.NBTWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class PolarWriter {
    private PolarWriter() {}

    public static byte[] write(@NotNull PolarWorld world) {
        // Write the compressed content first
        var content = new ByteBuffer();
        content.writeByte(world.minSection());
        content.writeByte(world.maxSection());
        content.writeCollection(world.chunks(), PolarWriter::writeChunk);
        ByteBuffer byteBuf = new ByteBuffer();
        byteBuf.writeInt(PolarWorld.MAGIC_NUMBER);
        byteBuf.writeShort(PolarWorld.LATEST_VERSION);
        byteBuf.writeByte((byte) world.compression().ordinal());
        switch (world.compression()) {
            case NONE -> {
                byteBuf.writeByteArray(content.toBytes());
            }
            case ZSTD -> {
                byteBuf.writeByteArray(Zstd.compress(content.toBytes()));
            }
        }
        // Create final buffer
        return byteBuf.toBytes();
    }

    private static void writeChunk(@NotNull ByteBuffer buffer, @NotNull PolarChunk chunk) {
        buffer.writeVarInt(chunk.x());
        buffer.writeVarInt(chunk.z());

        for (var section : chunk.sections()) {
            writeSection(buffer, section);
        }
        buffer.writeCollection(chunk.blockEntities(), PolarWriter::writeBlockEntity);

        //todo heightmaps
        buffer.writeInt(PolarChunk.HEIGHTMAP_NONE);

        buffer.writeByteArray(chunk.userData());
    }

    private static void writeSection(@NotNull ByteBuffer buffer, @NotNull PolarSection section) {
        buffer.writeBoolean(section.isEmpty());
        if (section.isEmpty()) return;

        // Blocks
        String[] blockPalette = section.blockPalette();
        buffer.writeCollection(Arrays.stream(blockPalette).toList(), ByteBuffer::writeString);
        if (blockPalette.length > 1) {
            var blockData = section.blockData();
            var bitsPerEntry = (int) Math.ceil(Math.log(blockPalette.length) / Math.log(2));
            if (bitsPerEntry < 1) bitsPerEntry = 1;
            buffer.writeCollection(Arrays.stream(PaletteUtil.pack(blockData, bitsPerEntry)).boxed().toList(), ByteBuffer::writeLong);
        }

        // Biomes
        var biomePalette = section.biomePalette();
        buffer.writeCollection(Arrays.stream(biomePalette).toList(), ByteBuffer::writeString);
        if (biomePalette.length > 1) {
            var biomeData = section.biomeData();
            var bitsPerEntry = (int) Math.ceil(Math.log(biomePalette.length) / Math.log(2));
            if (bitsPerEntry < 1) bitsPerEntry = 1;
            buffer.writeCollection(Arrays.stream(PaletteUtil.pack(biomeData, bitsPerEntry)).boxed().toList(), ByteBuffer::writeLong);
        }

        // Light
        buffer.writeBoolean(section.hasBlockLightData());
        if (section.hasBlockLightData())
            buffer.writeByteArray(section.blockLight());
        buffer.writeBoolean(section.hasSkyLightData());
        if (section.hasSkyLightData())
            buffer.writeByteArray(section.skyLight());
    }

    private static void writeBlockEntity(@NotNull ByteBuffer buffer, @NotNull PolarChunk.BlockEntity blockEntity) {
        var index = ChunkUtils.getBlockIndex(blockEntity.x(), blockEntity.y(), blockEntity.z());
        buffer.writeInt(index);
        buffer.writeBoolean(blockEntity.id() == null);
        buffer.writeString(blockEntity.id());
        buffer.writeBoolean(blockEntity.data() == null);
        writeNbt(buffer, blockEntity.data());
    }

    private static void writeNbt(ByteBuffer buffer, NBTCompound compound) {
        NBTWriter nbtWriter = null;
        nbtWriter = new NBTWriter(new OutputStream() {
            @Override
            public void write(int b) {
                buffer.writeByte((byte) b);
            }
        }, CompressedProcesser.NONE);
        try {
            nbtWriter.writeNamed("", compound);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
