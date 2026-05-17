package com.github.ars_zero.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {

    public static ForgeConfigSpec SERVER_CONFIG;
    public static ForgeConfigSpec.BooleanValue ALLOW_NON_OP_ANCHOR_ON_PLAYERS;
    public static ForgeConfigSpec.IntValue LARGE_EXPLOSION_MAX_BLOCKS_PER_TICK;
    public static ForgeConfigSpec.IntValue DEFAULT_MULTIPHASE_DEVICE_TICK_DELAY;
    /** TerraBlender region weight for Blight Forest land-biome replacement. */
    public static ForgeConfigSpec.IntValue BLIGHT_FOREST_WEIGHT;
    /** Spell power bonus granted per filial item (held offhand or embedded in staff). */
    public static ForgeConfigSpec.IntValue FILIAL_POWER_BONUS;

    static {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

        SERVER_BUILDER.comment("Anchor Effect Settings").push("anchor_effect");
        ALLOW_NON_OP_ANCHOR_ON_PLAYERS = SERVER_BUILDER.comment(
                "Allow non-OP players to use Anchor effect on other players.",
                "When set to false (default), only OP players can anchor other players.",
                "When set to true, any player can anchor other players.").define("allowNonOpAnchorOnPlayers", false);
        SERVER_BUILDER.pop();

        SERVER_BUILDER.comment("Large Explosion Settings").push("large_explosion");
        LARGE_EXPLOSION_MAX_BLOCKS_PER_TICK = SERVER_BUILDER.comment(
                "Hard per-tick block destruction budget for large explosions.")
                .defineInRange("maxBlocksPerTick", 256, 1, 1000000);
        SERVER_BUILDER.pop();

        SERVER_BUILDER.comment("Multiphase cast device settings").push("multiphase");
        DEFAULT_MULTIPHASE_DEVICE_TICK_DELAY = SERVER_BUILDER.comment(
                "Default tick delay (in ticks) for multiphase device slots. Minimum 1 (20 times per second).")
                .defineInRange("defaultTickDelay", 10, 1, 20);
        SERVER_BUILDER.pop();

        SERVER_BUILDER.comment("Blight forest TerraBlender land replacement settings.").push("blight_forest");
        BLIGHT_FOREST_WEIGHT = SERVER_BUILDER.comment(
                "TerraBlender region weight for replacing vanilla forest and taiga land biome slots with blight forest.",
                "Use 0 to disable. Values above 1 are clamped at runtime to avoid unstable terrain in heavily modded worldgen.")
                .defineInRange("weight", 1, 0, 1);
        SERVER_BUILDER.pop();

        SERVER_BUILDER.comment("Filial item settings").push("filial");
        FILIAL_POWER_BONUS = SERVER_BUILDER.comment(
                "Spell power bonus granted per filial (held offhand or embedded in staff).")
                .defineInRange("powerBonus", 3, 0, 100);
        SERVER_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();
    }
}
