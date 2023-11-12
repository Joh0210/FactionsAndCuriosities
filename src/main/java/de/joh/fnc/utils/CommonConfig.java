package de.joh.fnc.utils;

import de.joh.fnc.effect.EffectInit;
import de.joh.fnc.event.additional.ShouldCauseWildMagicEvent;
import net.minecraftforge.common.ForgeConfigSpec;

/**
 * This file creates the entire Common Configs of this mod.
 * @author Joh0210
 */
public class CommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> LIVING_BOMB_BREAK_BLOCKS;

    /**
     * How many ticks should be waited, till Wild Magic can be applied again?
     */
    public static final ForgeConfigSpec.ConfigValue<Integer> WILD_MAGIC_COOLDOWN;

    /**
     * How likely is a wild magic effect if the target is part of the Wild Faction?
     * @see ShouldCauseWildMagicEvent
     */
    public static final ForgeConfigSpec.ConfigValue<Integer> WILD_MAGIC_CHANCE;

    /**
     * How likely is a wild magic effect when the Target has a Wild Magic Potion Effect ({@link EffectInit#GOOD_WILD_MAGIC}, {@link EffectInit#WILD_MAGIC}, {@link EffectInit#BAD_WILD_MAGIC})?
     * @see ShouldCauseWildMagicEvent
     */
    public static final ForgeConfigSpec.ConfigValue<Integer> FORCED_WILD_MAGIC_CHANCE;

    /**
     * How many HP should be healed with the Glittering Potato?
     */
    public static final ForgeConfigSpec.ConfigValue<Integer> GLITTERING_POTATO_HEAL_AMOUNT;

    /**
     * How likely is it for the Glittering Potato to explode?
     */
    public static final ForgeConfigSpec.ConfigValue<Integer> GLITTERING_POTATO_HURT_CHANCE;

    static {
        BUILDER.push("General Configs");

        BUILDER.push("Living Bomb");
        LIVING_BOMB_BREAK_BLOCKS = BUILDER.define("Should the living bomb effect break blocks?", false);
        BUILDER.pop();

        BUILDER.push("Wild Magic");
        WILD_MAGIC_COOLDOWN = BUILDER.defineInRange("How many seconds should be waited, till Wild Magic could be applied again?", 5, 2, 300);
        WILD_MAGIC_CHANCE = BUILDER.defineInRange("How likely is a wild magic effect if the target is part of the Wild Faction?", 10, 0, 100);
        FORCED_WILD_MAGIC_CHANCE = BUILDER.defineInRange("How likely is a wild magic effect when the target has a Wild Magic Potion Effect?", 50, 0, 100);
        BUILDER.pop();

        BUILDER.push("Glittering Potato");
        GLITTERING_POTATO_HEAL_AMOUNT = BUILDER.defineInRange("How many HP should be healed with the Glittering Potato?", 4, 1, 20);
        GLITTERING_POTATO_HURT_CHANCE = BUILDER.defineInRange("How likely is it for the Glittering Potato to explode?", 20, 0, 99);
        BUILDER.pop();


        BUILDER.pop();

        SPEC = BUILDER.build();
    }

    public static int getWildMagicCooldown(){
        return WILD_MAGIC_COOLDOWN.get() * 20;
    }
}
