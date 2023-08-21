package me.combimagnetron.lagoon.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import me.combimagnetron.lagoon.data.Identifier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviderType;
import net.minecraft.world.Difficulty;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.PrimaryLevelData;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.data.BlockData;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.IntSupplier;
import java.util.stream.Stream;

public class GameLevel {
    private final ServerLevel serverLevel;
    private static String name;

    public GameLevel(Identifier identifier, MeridianWorld meridianWorld) {
        this.serverLevel = new ServerLevel(
                MinecraftServer.getServer(),
                MinecraftServer.getServer().executor,
                LevelAccess.levelStorageAccess(),
                LevelData.primaryLevelData(),
                Level.END,
                LevelDimensionStem.levelStem(),
                ChunkProgressListenerDummy.chunkProgressListenerDummy(),
                false,
                0L,
                List.of(),
                false,
                World.Environment.THE_END,
                ChunkGeneratorImpl.chunkGenerator(meridianWorld),
                BiomeProviderImpl.biomeProvider()
        );
        name = "comet_" + identifier.string() + "_" + UUID.randomUUID().toString().substring(0, 6);
    }

    static final class LevelAccess {
        public static LevelStorageSource.LevelStorageAccess levelStorageAccess() {
            try {
                final Path worldFolder;
                worldFolder = Files.createTempDirectory(name).toAbsolutePath();
                FileUtils.forceDeleteOnExit(worldFolder.toFile());
                LevelStorageSource levelStorageSource = new LevelStorageSource(worldFolder, worldFolder, DataFixers.getDataFixer());
                return levelStorageSource.new LevelStorageAccess(name, LevelStem.END);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static final class LevelData {

        public static PrimaryLevelData primaryLevelData() {
            return new PrimaryLevelData(
                    new LevelSettings(name,
                            GameType.SURVIVAL,
                            false,
                            Difficulty.NORMAL,
                            true,
                            new GameRules(),
                            new WorldDataConfiguration(
                                    new DataPackConfig(List.of(), List.of()),
                                    FeatureFlagSet.of()
                            )
                    ),
                    new WorldOptions(0L, false, false),
                    PrimaryLevelData.SpecialWorldProperty.NONE,
                    Lifecycle.stable()
            );
        }

    }

    static final class LevelDimensionStem {

        public static LevelStem levelStem() {
            IntProvider intProvider = new IntProvider() {
                @Override
                public int sample(RandomSource random) {
                    return 15;
                }

                @Override
                public int getMinValue() {
                    return 15;
                }

                @Override
                public int getMaxValue() {
                    return 15;
                }

                @Override
                public @NotNull IntProviderType<?> getType() {
                    return IntProviderType.CONSTANT;
                }
            };
            DimensionType dimensionType = new DimensionType(
                    OptionalLong.of(0L),
                    false,
                    false,
                    false,
                    true,
                    1.0,
                    true,
                    false,
                    -64,
                    255,
                    64,
                    TagKey.create(Registries.BLOCK, ResourceLocation.of("air", ' ')),
                    ResourceLocation.of("", ' '),
                    0.20f,
                    new DimensionType.MonsterSettings(true, false, intProvider, 15)
            );
            return new LevelStem(Holder.direct(dimensionType), null);
        }
    }

    static final class ChunkGeneratorImpl extends org.bukkit.generator.ChunkGenerator {
        private final MeridianWorld meridianWorld;

        private ChunkGeneratorImpl(MeridianWorld world) {
            this.meridianWorld = world;
        }

        public static ChunkGeneratorImpl chunkGenerator(MeridianWorld meridianWorld) {
            return new ChunkGeneratorImpl(meridianWorld);
        }


        @Override
        public void generateNoise(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
            MeridianChunk meridianChunk = meridianWorld.chunkAt(chunkX, chunkZ);
            Map<Integer, Set<BlockData>> blockData = new HashMap<>();
            for (MeridianSection section : meridianChunk.sections()) {
                int y = -64;
                Set<BlockData> blockDataSet = new LinkedHashSet<>();
                for (String string : section.blockPalette()) {
                    blockDataSet.add(Bukkit.createBlockData(string));
                }
                blockData.put(y, blockDataSet);
            }
            for(int y = chunkData.getMinHeight(); y <= chunkData.getMaxHeight(); y++) {
                Collection<BlockData> blockDataCollection = blockData.get(y);
                for(int x = 0; x < 16; x++) {
                    for(int z = 0; z < 16; z++) {
                        chunkData.setBlock(x, y, z, blockDataCollection.iterator().next());
                    }
                }
            }
        }

    }

    static final class BiomeProviderImpl extends BiomeProvider {

        private BiomeProviderImpl() {
        }

        public static BiomeProviderImpl biomeProvider() {
            return new BiomeProviderImpl();
        }

        @Override
        public @NotNull Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z) {
            return Biome.END_BARRENS;
        }

        @Override
        public @NotNull List<Biome> getBiomes(@NotNull WorldInfo worldInfo) {
            return List.of(Biome.END_BARRENS);
        }
    }

    static final class ChunkProgressListenerDummy implements ChunkProgressListener {

        public static ChunkProgressListenerDummy chunkProgressListenerDummy() {
            return new ChunkProgressListenerDummy();
        }

        @Override
        public void updateSpawnPos(ChunkPos spawnPos) {

        }

        @Override
        public void onStatusChange(ChunkPos pos, @Nullable ChunkStatus status) {

        }

        @Override
        public void start() {

        }

        @Override
        public void stop() {

        }

        @Override
        public void setChunkRadius(int radius) {

        }
    }


}
