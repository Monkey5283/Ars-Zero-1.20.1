package com.github.ars_zero.common.world.structure;

import com.github.ars_zero.registry.ModBlocks;
import com.github.ars_zero.registry.ModWorldgen;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public class WorldgenSanitizerProcessor extends StructureProcessor {

    public static final MapCodec<WorldgenSanitizerProcessor> CODEC = MapCodec.unit(new WorldgenSanitizerProcessor());

    private static final Set<String> ALLOWED_NAMESPACES = Set.of(
            "minecraft",
            "ars_zero",
            "ars_nouveau",
            "ars_elemental"
    );

    @Override
    @Nullable
    public StructureTemplate.StructureBlockInfo processBlock(
            @Nonnull LevelReader level,
            @Nonnull BlockPos offset,
            @Nonnull BlockPos pos,
            @Nonnull StructureTemplate.StructureBlockInfo blockInfo,
            @Nonnull StructureTemplate.StructureBlockInfo relativeBlockInfo,
            @Nonnull StructurePlaceSettings settings) {

        BlockState state = relativeBlockInfo.state();
        if (state.isAir()) {
            return relativeBlockInfo;
        }

        ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock());
        if (blockId != null && ALLOWED_NAMESPACES.contains(blockId.getNamespace())) {
            return relativeBlockInfo;
        }

        Block fallback = ModBlocks.CORRUPTED_BLOCKS.containsKey("smooth_corrupted_sourcestone")
                ? ModBlocks.CORRUPTED_BLOCKS.get("smooth_corrupted_sourcestone").get()
                : Blocks.STONE;

        return new StructureTemplate.StructureBlockInfo(
                relativeBlockInfo.pos(),
                fallback.defaultBlockState(),
                null);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModWorldgen.WORLDGEN_SANITIZER_PROCESSOR.get();
    }
}
