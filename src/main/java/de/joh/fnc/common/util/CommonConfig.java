package de.joh.fnc.common.util;

import de.joh.fnc.api.event.ShouldCauseWildMagicEvent;
import de.joh.fnc.common.effect.smite.DispellingSmite;
import de.joh.fnc.common.init.EffectInit;
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

    /**
     * What is the maximum amount of Damage Smites can do in total (Smite Stacking included)
     */
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_SMITE_DAMAGE;

    /**
     * Duration of Smite Effects (in s)
     */
    public static final ForgeConfigSpec.ConfigValue<Integer>  SMITE_DURATION;

    /**
     * Determines the factor by which Dispelling Smite's magnitude is increased when determining whether a mob effect can be removed.
     * <br> Duration (in s) * Amplifier <= Spell-Magnitude * this Factor
     * <br> -> If the equation is True, the effect can be removed
     * @see DispellingSmite
     */
    public static final ForgeConfigSpec.ConfigValue<Integer>  DISPELLING_SMITE_MAGNITUDE_MOD;

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

        BUILDER.push("Smite");
        MAX_SMITE_DAMAGE = BUILDER.defineInRange("What is the maximum amount of Damage Smites can do in total (Smite Stacking included)", 20, 1, 100);
        SMITE_DURATION = BUILDER.defineInRange("Duration of Smite Effects (in s)", 30, 5, 300);
        DISPELLING_SMITE_MAGNITUDE_MOD = BUILDER.comment("Determines the factor by which Dispelling Smite's magnitude is increased when determining whether a mob effect can be removed.").comment("Duration (in s) * Amplifier <= Spell-Magnitude * this Factor").comment("If the equation is true, the effect can be removed").defineInRange("Dispelling Smite Mod", 30, 5, 300);
        BUILDER.pop();

        BUILDER.pop();

        SPEC = BUILDER.build();
    }

    public static int getWildMagicCooldown(){
        return WILD_MAGIC_COOLDOWN.get() * 20;
    }
}
