package de.joh.fnc.datagen;

import com.mna.api.affinity.Affinity;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.common.faction.ResourceIDs;
import de.joh.fnc.common.init.ItemInit;
import de.joh.fnc.common.init.SpellInit;
import de.joh.tnl.api.datagen.MnaRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

import static com.mna.items.ItemInit.*;
import static de.joh.tnl.api.datagen.EldrinAltarRecipeBuilder.eldrinAltar;
import static de.joh.tnl.api.datagen.MagiChemDistillationRecipeBuilder.distillation;
import static de.joh.tnl.api.datagen.MagiChemSublimationRecipeBuilder.sublimation;
import static de.joh.tnl.api.datagen.ManaweavePatterns.*;
import static de.joh.tnl.api.datagen.ManaweavingRecipeBuilder.manaweaving;
import static de.joh.tnl.api.datagen.SpellPartRecipeBuilder.component;
import static de.joh.tnl.api.datagen.SpellPartRecipeBuilder.shape;

/**
 * The recipes are grouped by the item they craft instead of by crafting station, so the upgrade stages of an item
 * stay together.
 * <br>The enchanting recipe {@code manaweaving/enchanting/holy_smite} is maintained by hand, because the manaweaving
 * builder supports no {@code enchant}.
 * @author Joh0210
 */
public class ModRecipeProvider extends MnaRecipeProvider {
    public ModRecipeProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, FactionsAndCuriosities.MOD_ID, existingFileHelper);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> writer) {
        materials(writer);
        rings(writer);
        braceletsAndBelts(writer);
        weaponsAndTools(writer);
        armor(writer);
        miscellaneous(writer);
        components(writer);
        shapes(writer);
        magichem(writer);
    }

    private void materials(Consumer<FinishedRecipe> writer) {
        // Tier 2, because the Ritual of the Wild (tier 2) needs a dice to join the Wild Courts
        save(writer, "dice_six", manaweaving(ItemInit.DICE.get(), 2)
                .item(Items.GOLD_INGOT, 3)
                .items(MOTE_AIR.get(), Items.BLACK_DYE, CHIMERITE_GEM.get(), RITUAL_FOCUS_LESSER.get(), MOTE_ARCANE.get())
                .patterns(SQUARE, TRIANGLE));

        save(writer, "dice_twenty", eldrinAltar(ItemInit.DICE_20.get(), 4)
                .items(ItemInit.DICE.get(), GREATER_MOTE_AIR.get(), GREATER_MOTE_ARCANE.get(), MANA_CRYSTAL_FRAGMENT.get())
                .item(Items.GOLD_BLOCK, 2)
                .power(1000, Affinity.ARCANE, Affinity.WIND));
    }

    private void rings(Consumer<FinishedRecipe> writer) {
        save(writer, "aura_fraud_ring", manaweaving(ItemInit.AURA_FRAUD_RING.get(), 3)
                .requiredFaction(ResourceIDs.FACTION_PALADIN_ID)
                .items(MUNDANE_RING.get(), RUNE_AURA.get(), MANA_CRYSTAL_FRAGMENT.get(), MOTE_ARCANE.get(),
                        CHIMERITE_GEM.get())
                .item(PURIFIED_VINTEUM_DUST.get(), 2)
                .patterns(DIAMOND, BOLT, DIAMOND));

        save(writer, "four_leaf_clover_ring", manaweaving(ItemInit.FOUR_LEAF_CLOVER_RING.get(), 3)
                .requiredFaction(ResourceIDs.FACTION_WILD_ID)
                .items(ItemInit.DICE.get(), MUNDANE_RING.get(), RUNE_DEFENSE.get())
                .tag(ItemTags.LEAVES)
                .tag(ItemTags.LEAVES)
                .tag(ItemTags.LEAVES)
                .tag(ItemTags.LEAVES)
                .patterns(CIRCLE, CIRCLE, CIRCLE));

        save(writer, "frenzy_ring", manaweaving(ItemInit.FRENZY_RING.get(), 3)
                .requiredFaction(ResourceIDs.FACTION_PALADIN_ID)
                .items(MUNDANE_RING.get(), MOTE_FIRE.get(), STONE_RUNE_RED.get())
                .item(Items.BLAZE_POWDER, 2)
                .item(Items.CHAIN, 2)
                .patterns(SQUARE, KNOT4, TRIANGLE));

        save(writer, "frenzy_ring_greater", eldrinAltar(ItemInit.FRENZY_RING_MAJOR.get(), 5)
                .requiredFaction(ResourceIDs.FACTION_PALADIN_ID)
                .items(ItemInit.FRENZY_RING.get(), GREATER_MOTE_FIRE.get(), RUNE_FIRE.get(), STONE_RUNE_RED.get())
                .item(LIVING_FLAME.get(), 2)
                .item(Items.BLAZE_ROD, 2)
                .power(2500, Affinity.FIRE)
                .power(1000, Affinity.ENDER));

        save(writer, "heavy_weapon_ring", manaweaving(ItemInit.HEAVY_WEAPON_RING.get(), 3)
                .requiredFaction(ResourceIDs.FACTION_PALADIN_ID)
                .items(MUNDANE_RING.get(), MOTE_EARTH.get(), Items.ANVIL, Items.IRON_SWORD, Items.IRON_AXE)
                .item(Items.IRON_BLOCK, 2)
                .patterns(SQUARE, DIAMOND, SQUARE));

        save(writer, "heavy_weapon_ring_greater", eldrinAltar(ItemInit.HEAVY_WEAPON_RING_GREATER.get(), 5)
                .requiredFaction(ResourceIDs.FACTION_PALADIN_ID)
                .items(ItemInit.HEAVY_WEAPON_RING.get())
                .item(GREATER_MOTE_EARTH.get(), 2)
                .items(RUNE_EARTH.get(), Items.NETHERITE_INGOT, Items.ANVIL)
                .item(Items.OBSIDIAN, 2)
                .power(2500, Affinity.EARTH)
                .power(1000, Affinity.ARCANE));

        save(writer, "ring_of_wild_luck", manaweaving(ItemInit.RING_OF_WILD_LUCK.get(), 4)
                .requiredFaction(ResourceIDs.FACTION_WILD_ID)
                .items(ItemInit.DICE_20.get(), MUNDANE_RING.get(), GREATER_MOTE_ARCANE.get())
                .item(Items.RABBIT_FOOT, 2)
                .item(Items.GOLD_BLOCK, 2)
                .patterns(CIRCLE, STAR, SPLIT_TRIANGLE));

        // The rings of second chance share dice, ring and a candle; the rest doubles what the ring gives a second time
        save(writer, "second_attack_ring", manaweaving(ItemInit.SECOND_ATTACK_RING.get(), 4)
                .requiredFaction(ResourceIDs.FACTION_WILD_ID)
                .items(ItemInit.DICE_20.get(), MUNDANE_RING.get(), Items.YELLOW_CANDLE, GREATER_MOTE_AIR.get())
                .item(Items.IRON_SWORD, 2)
                .patterns(KNOT3, KNOT4, KNOT3));

        save(writer, "second_drop_ring", manaweaving(ItemInit.SECOND_DROP_RING.get(), 4)
                .requiredFaction(ResourceIDs.FACTION_WILD_ID)
                .items(ItemInit.DICE_20.get(), MUNDANE_RING.get(), Items.GREEN_CANDLE, GREATER_MOTE_WATER.get())
                .item(Items.GLOWSTONE_DUST, 2)
                .patterns(KNOT, KNOT4, KNOT2));

        save(writer, "second_protection_ring", manaweaving(ItemInit.SECOND_PROTECTION_RING.get(), 4)
                .requiredFaction(ResourceIDs.FACTION_WILD_ID)
                .items(ItemInit.DICE_20.get(), MUNDANE_RING.get(), Items.BLACK_CANDLE, GREATER_MOTE_EARTH.get())
                .item(Items.SHIELD, 2)
                .patterns(KNOT2, KNOT3, KNOT));

        save(writer, "smiting_ring", manaweaving(ItemInit.SMITING_RING.get(), 5)
                .requiredFaction(ResourceIDs.FACTION_PALADIN_ID)
                .items(MUNDANE_RING.get(), Items.NETHER_STAR, Items.WOODEN_SWORD, Items.STONE_SWORD, Items.IRON_SWORD,
                        Items.GOLDEN_SWORD, Items.DIAMOND_SWORD, Items.NETHERITE_SWORD)
                .patterns(INFINITY, SPLIT_TRIANGLE, HOURGLASS, INVERTED_SPLIT_TRIANGLE, INFINITY));
    }

    private void braceletsAndBelts(Consumer<FinishedRecipe> writer) {
        save(writer, "black_cat_bracelet", manaweaving(ItemInit.BLACK_CAT_BRACELET.get(), 3)
                .requiredFaction(ResourceIDs.FACTION_WILD_ID)
                .items(ItemInit.DICE.get(), MUNDANE_BRACELET.get(), MOTE_ARCANE.get(), Items.FERMENTED_SPIDER_EYE)
                .item(Items.BLACK_DYE, 2)
                .patterns(TRIANGLE, SPLIT_TRIANGLE, DIAMOND));

        save(writer, "blood_lust_bracelet", manaweaving(ItemInit.BLOOD_LUST_BRACELET.get(), 3)
                .requiredFaction(ResourceIDs.FACTION_PALADIN_ID)
                .items(Items.GHAST_TEAR, Items.GOLDEN_APPLE, MANA_CRYSTAL_FRAGMENT.get(), MUNDANE_BRACELET.get(),
                        Items.DIAMOND_SWORD, STONE_RUNE_RED.get())
                .patterns(SQUARE, TRIANGLE, SQUARE));

        save(writer, "time_belt", manaweaving(ItemInit.TIME_BELT.get(), 4)
                .requiredFaction(ResourceIDs.FACTION_PALADIN_ID)
                .items(BELT_BUCKLE.get(), Items.CLOCK, GREATER_MOTE_ARCANE.get(), STONE_RUNE_MAGENTA.get(),
                        Items.NETHER_WART)
                .item(Items.REDSTONE, 2)
                .patterns(HOURGLASS, HOURGLASS, HOURGLASS));
    }

    private void weaponsAndTools(Consumer<FinishedRecipe> writer) {
        save(writer, "brimstone_sword", eldrinAltar(ItemInit.BRIMSTONE_SWORD.get(), 4)
                .requiredFaction(ResourceIDs.FACTION_PALADIN_ID)
                .items(Items.NETHERITE_SWORD, BRIMSTONE_CHARM.get(), GREATER_MOTE_FIRE.get())
                .item(LIVING_FLAME.get(), 2)
                .item(Items.GHAST_TEAR, 2)
                .power(1500, Affinity.FIRE)
                .power(500, Affinity.EARTH));

        save(writer, "blessed_bow", eldrinAltar(ItemInit.BLESSED_BOW.get(), 5)
                .requiredFaction(ResourceIDs.FACTION_PALADIN_ID)
                .items(Items.BOW, Items.NETHERITE_SWORD, GREATER_MOTE_AIR.get(), GREATER_MOTE_ARCANE.get(),
                        RUNE_AIR.get())
                .item(Items.SPECTRAL_ARROW, 2)
                .power(2500, Affinity.WIND)
                .power(1500, Affinity.ARCANE));

        // The pickaxe has netherite stats, the ores stand for its random drops
        save(writer, "wild_pickaxe", manaweaving(ItemInit.WILD_PICKAXE.get(), 4)
                .requiredFaction(ResourceIDs.FACTION_WILD_ID)
                .items(ItemInit.DICE.get(), Items.GOLDEN_PICKAXE, Items.NETHERITE_INGOT, MOTE_EARTH.get(),
                        Items.DIAMOND, Items.EMERALD, Items.LAPIS_LAZULI, Items.COAL)
                .patterns(SPLIT_TRIANGLE, SQUARE, DIAMOND));
    }

    // Sized like the M&A faction armor: the chestplate gets the nether star and only the main affinity
    private void armor(Consumer<FinishedRecipe> writer) {
        save(writer, "divine_helmet", eldrinAltar(ItemInit.DIVINE_HELMET.get(), 5)
                .requiredFaction(ResourceIDs.FACTION_PALADIN_ID)
                .items(Items.NETHERITE_HELMET, GREATER_MOTE_ARCANE.get(), GREATER_MOTE_EARTH.get())
                .item(RUNE_DEFENSE.get(), 2)
                .item(Items.GOLD_BLOCK, 2)
                .power(2500, Affinity.ARCANE)
                .power(1000, Affinity.EARTH));

        save(writer, "divine_chestplate", eldrinAltar(ItemInit.DIVINE_CHESTPLATE.get(), 5)
                .requiredFaction(ResourceIDs.FACTION_PALADIN_ID)
                .items(Items.NETHERITE_CHESTPLATE, Items.NETHER_STAR)
                .item(GREATER_MOTE_ARCANE.get(), 2)
                .item(RUNE_DEFENSE.get(), 2)
                .item(Items.GOLD_BLOCK, 2)
                .power(2500, Affinity.ARCANE));

        save(writer, "divine_legging", eldrinAltar(ItemInit.DIVINE_LEGGING.get(), 5)
                .requiredFaction(ResourceIDs.FACTION_PALADIN_ID)
                .items(Items.NETHERITE_LEGGINGS, GREATER_MOTE_ARCANE.get(), GREATER_MOTE_EARTH.get())
                .item(RUNE_DEFENSE.get(), 2)
                .item(Items.GOLD_BLOCK, 2)
                .power(2500, Affinity.ARCANE)
                .power(1000, Affinity.EARTH));

        save(writer, "divine_boots", eldrinAltar(ItemInit.DIVINE_BOOTS.get(), 5)
                .requiredFaction(ResourceIDs.FACTION_PALADIN_ID)
                .items(Items.NETHERITE_BOOTS, GREATER_MOTE_ARCANE.get(), GREATER_MOTE_EARTH.get())
                .item(RUNE_DEFENSE.get(), 2)
                .item(Items.GOLD_BLOCK, 2)
                .power(2500, Affinity.ARCANE)
                .power(1000, Affinity.EARTH));

        save(writer, "mischief_helmet", eldrinAltar(ItemInit.MISCHIEF_HELMET.get(), 5)
                .requiredFaction(ResourceIDs.FACTION_WILD_ID)
                .items(Items.GOLDEN_HELMET, ItemInit.DICE_20.get(), GREATER_MOTE_ARCANE.get(), RUNE_ARCANE.get(),
                        RUNE_AIR.get(), RUNIC_SILK.get(), INFUSED_THREAD.get())
                .power(2500, Affinity.ARCANE)
                .power(1000, Affinity.WIND));

        save(writer, "mischief_chestplate", eldrinAltar(ItemInit.MISCHIEF_CHESTPLATE.get(), 5)
                .requiredFaction(ResourceIDs.FACTION_WILD_ID)
                .items(Items.GOLDEN_CHESTPLATE, ItemInit.DICE_20.get())
                .item(GREATER_MOTE_ARCANE.get(), 2)
                .items(RUNE_ARCANE.get(), RUNIC_SILK.get(), INFUSED_THREAD.get())
                .item(Items.GREEN_WOOL, 2)
                .power(2500, Affinity.ARCANE));

        save(writer, "mischief_legging", eldrinAltar(ItemInit.MISCHIEF_LEGGING.get(), 5)
                .requiredFaction(ResourceIDs.FACTION_WILD_ID)
                .items(Items.GOLDEN_LEGGINGS, ItemInit.DICE_20.get(), GREATER_MOTE_ARCANE.get(), RUNE_ARCANE.get(),
                        RUNE_ENDER.get(), RUNIC_SILK.get(), INFUSED_THREAD.get(), Items.GREEN_WOOL)
                .power(2500, Affinity.ARCANE)
                .power(1000, Affinity.ENDER));

        save(writer, "mischief_boots", eldrinAltar(ItemInit.MISCHIEF_BOOTS.get(), 5)
                .requiredFaction(ResourceIDs.FACTION_WILD_ID)
                .items(Items.GOLDEN_BOOTS, ItemInit.DICE_20.get(), GREATER_MOTE_ARCANE.get(), RUNE_ARCANE.get(),
                        RUNE_AIR.get(), RUNIC_SILK.get(), INFUSED_THREAD.get())
                .power(2500, Affinity.ARCANE)
                .power(1000, Affinity.WIND));
    }

    private void miscellaneous(Consumer<FinishedRecipe> writer) {
        save(writer, "deck_of_many", eldrinAltar(ItemInit.DECK_OF_MANY.get(), 5)
                .requiredFaction(ResourceIDs.FACTION_WILD_ID)
                .items(Items.PAPER, ItemInit.DICE_20.get(), Items.NETHER_STAR, Items.TNT, Items.DIAMOND_BLOCK,
                        Items.ENDER_PEARL, RUNE_AIR.get(), RUNE_ARCANE.get(), RUNE_ENDER.get())
                .power(1500, ALL_AFFINITIES));

        save(writer, "glittering_potato", manaweaving(ItemInit.GLITTERING_POTATO.get(), 3)
                .quantity(6)
                .requiredFaction(ResourceIDs.FACTION_WILD_ID)
                .items(CHIMERITE_GEM.get(), Items.GUNPOWDER)
                .item(Items.BAKED_POTATO, 6)
                .patterns(CIRCLE, TRIANGLE));
    }

    // Smites start with a sword of their tier (iron, diamond, netherite), blood magic with a red stone rune
    private void components(Consumer<FinishedRecipe> writer) {
        save(writer, component(SpellInit.BLOOD_SACRIFICE, 3)
                .items(ItemInit.SACRIFICIAL_DAGGER.get(), STONE_RUNE_RED.get(), Items.GLASS_BOTTLE, Items.REDSTONE,
                        VINTEUM_DUST.get())
                .patterns(TRIANGLE, INVERTED_TRIANGLE));

        save(writer, component(SpellInit.BOOMING_SMITE, 4)
                .items(Items.DIAMOND_SWORD, Items.GUNPOWDER, Items.TNT, MOTE_FIRE.get(),
                        com.mna.blocks.BlockInit.DESERT_NOVA.get())
                .patterns(CIRCLE, DIAMOND, SQUARE));

        save(writer, component(SpellInit.BURNING_SMITE, 3)
                .items(Items.IRON_SWORD, STONE_RUNE_ORANGE.get(), Items.FLINT_AND_STEEL, Items.BLAZE_POWDER)
                .patterns(TRIANGLE, TRIANGLE));

        save(writer, component(SpellInit.CAUSE_WILD_MAGIC, 5)
                .items(ItemInit.BLACK_CAT_BRACELET.get(), ItemInit.DICE_20.get())
                .item(Items.ENDER_EYE, 2)
                .patterns(STAR, INFINITY, HOURGLASS));

        save(writer, component(SpellInit.CRIPPLING_SMITE, 3)
                .items(Items.IRON_SWORD, Items.FERMENTED_SPIDER_EYE, Items.COBWEB, Items.CHAIN)
                .patterns(INVERTED_TRIANGLE, SQUARE));

        save(writer, component(SpellInit.DISPELLING_SMITE, 4)
                .items(Items.DIAMOND_SWORD, Items.GLASS_BOTTLE, Items.MILK_BUCKET, Items.GHAST_TEAR)
                .patterns(INVERTED_TRIANGLE, HOURGLASS, CIRCLE));

        save(writer, component(SpellInit.FINAL_DUAL, 4)
                .items(STONE_RUNE_RED.get(), Items.WITHER_ROSE, Items.BONE)
                .item(Items.IRON_SWORD, 2)
                .patterns(TRIANGLE, HOURGLASS, INVERTED_TRIANGLE));

        save(writer, component(SpellInit.GAMBLERS_BLOW_DEFENSIVE, 4)
                .items(ItemInit.DICE.get(), Items.GHAST_TEAR, ARCANE_ASH.get(), Items.SHIELD)
                .patterns(INVERTED_TRIANGLE, HOURGLASS, INVERTED_TRIANGLE));

        save(writer, component(SpellInit.GAMBLERS_BLOW_OFFENSIVE, 4)
                .items(ItemInit.DICE.get(), Items.GHAST_TEAR, ARCANE_ASH.get(), Items.IRON_SWORD)
                .patterns(TRIANGLE, HOURGLASS, TRIANGLE));

        save(writer, component(SpellInit.HEX, 4)
                .items(STONE_RUNE_RED.get(), Items.WITHER_ROSE, Items.FERMENTED_SPIDER_EYE, Items.ROTTEN_FLESH,
                        VINTEUM_DUST.get())
                .patterns(INVERTED_TRIANGLE, HOURGLASS, INVERTED_TRIANGLE));

        save(writer, component(SpellInit.HEXING_SMITE, 4)
                .items(Items.DIAMOND_SWORD, Items.WITHER_ROSE, Items.FERMENTED_SPIDER_EYE, Items.ROTTEN_FLESH,
                        VINTEUM_DUST.get())
                .patterns(INVERTED_TRIANGLE, HOURGLASS, INVERTED_TRIANGLE));

        save(writer, component(SpellInit.POSTPONEMENT, 5)
                .items(STONE_RUNE_RED.get(), STONE_RUNE_MAGENTA.get(), Items.CLOCK, GREATER_MOTE_ARCANE.get(),
                        Items.DRAGON_BREATH)
                .patterns(HOURGLASS, INFINITY, HOURGLASS));

        save(writer, component(SpellInit.RANDOM_TP, 3)
                .items(ItemInit.DICE.get(), MOTE_ENDER.get(), Items.ENDER_PEARL)
                .item(VINTEUM_DUST.get(), 2)
                .patterns(INVERTED_TRIANGLE, SQUARE));

        save(writer, component(SpellInit.SACRIFICIAL_LAMB, 4)
                .items(ItemInit.SACRIFICIAL_DAGGER.get(), STONE_RUNE_RED.get(), Items.MUTTON)
                .tag(ItemTags.WOOL)
                .items(MOTE_ARCANE.get())
                .patterns(TRIANGLE, INVERTED_TRIANGLE, CIRCLE));

        save(writer, component(SpellInit.SILENCING_SMITE, 5)
                .items(Items.NETHERITE_SWORD)
                .tag(ItemTags.WOOL)
                .tag(ItemTags.WOOL)
                .items(com.mna.blocks.BlockInit.AUM.get(), ARCANE_ASH.get())
                .patterns(INFINITY, SPLIT_TRIANGLE, DIAMOND));

        save(writer, component(SpellInit.WILD_DAMAGE, 3)
                .items(ItemInit.DICE.get(), STONE_RUNE_PURPLE.get(), VINTEUM_DUST.get())
                .item(Items.GOLDEN_SWORD, 2)
                .patterns(TRIANGLE, KNOT3));

        save(writer, component(SpellInit.WILD_MAGIC_COOLDOWN, 3)
                .items(ItemInit.DICE.get(), STONE_RUNE_MAGENTA.get(), Items.CLOCK, Items.REPEATER)
                .patterns(CIRCLE, CIRCLE));
    }

    private void shapes(Consumer<FinishedRecipe> writer) {
        save(writer, shape(SpellInit.COUNTER_ATTACK, 5)
                .items(Items.SHIELD, RUNE_DEFENSE.get(), GREATER_MOTE_ARCANE.get(), GREATER_MOTE_ENDER.get())
                .item(Items.CACTUS, 2)
                .patterns(SPLIT_TRIANGLE, INFINITY, HOURGLASS, INVERTED_SPLIT_TRIANGLE));

        save(writer, shape(SpellInit.SMITE, 5)
                .items(Items.NETHERITE_SWORD, GREATER_MOTE_AIR.get(), GREATER_MOTE_EARTH.get(),
                        GREATER_MOTE_WATER.get(), GREATER_MOTE_FIRE.get(), GREATER_MOTE_ARCANE.get(),
                        GREATER_MOTE_ENDER.get())
                .patterns(INFINITY, SPLIT_TRIANGLE, HOURGLASS, INVERTED_SPLIT_TRIANGLE));
    }

    private void magichem(Consumer<FinishedRecipe> writer) {
        save(writer, distillation(ItemInit.DICE.get())
                .admixture("luck", 12).admixture("change", 8).essentia("precious", 6).admixture("instrument", 2)
                .essentia("nigredo", 4));

        // alternative route: knucklebones, the materia replace the chimerite and the mote
        save(writer, sublimation(ItemInit.DICE.get(), 2)
                .stage(8)
                .items(Items.BONE, Items.GOLD_INGOT, Items.BONE, Items.BLACK_DYE)
                .admixture("luck", 20).admixture("change", 14).essentia("arcane", 12));

        save(writer, distillation(ItemInit.DICE_20.get())
                .admixture("luck", 40).admixture("change", 28).essentia("precious", 20).admixture("instrument", 4)
                .essentia("citrinitas", 6));

        // refinement route from the D6, the second stage stands for the arcane and wind power of the Eldrin Altar
        save(writer, sublimation(ItemInit.DICE_20.get(), 4)
                .stage(40)
                .items(ItemInit.DICE.get(), CHIMERITE_GEM.get(), Items.GOLD_BLOCK, CHIMERITE_GEM.get())
                .admixture("luck", 48).admixture("change", 32).admixture("crystal", 24)
                .stage(60)
                .items(MANA_CRYSTAL_FRAGMENT.get(), Items.GOLD_BLOCK)
                .essentia("arcane", 48).essentia("air", 48).admixture("luck", 32).essentia("citrinitas", 12));

        save(writer, distillation(ItemInit.AURA_FRAUD_RING.get())
                .admixture("mana", 24).admixture("lies", 16).admixture("light", 12).admixture("adornment", 4)
                .essentia("albedo", 5));

        save(writer, distillation(ItemInit.FOUR_LEAF_CLOVER_RING.get())
                .admixture("protection", 24).admixture("luck", 16).admixture("change", 8).admixture("adornment", 4)
                .essentia("albedo", 5));

        save(writer, distillation(ItemInit.FRENZY_RING.get())
                .admixture("violence", 24).admixture("blood", 16).admixture("light", 8).admixture("adornment", 4)
                .essentia("albedo", 5));

        save(writer, distillation(ItemInit.FRENZY_RING_MAJOR.get())
                .admixture("violence", 72).admixture("blood", 48).admixture("light", 24).admixture("adornment", 8)
                .essentia("rubedo", 10));

        save(writer, distillation(ItemInit.HEAVY_WEAPON_RING.get())
                .admixture("violence", 24).admixture("metal", 20).admixture("light", 8).admixture("adornment", 4)
                .essentia("albedo", 5));

        save(writer, distillation(ItemInit.HEAVY_WEAPON_RING_GREATER.get())
                .admixture("violence", 72).admixture("metal", 56).admixture("light", 24).admixture("adornment", 8)
                .essentia("rubedo", 10));

        save(writer, distillation(ItemInit.RING_OF_WILD_LUCK.get())
                .admixture("luck", 48).admixture("change", 24).essentia("precious", 16).admixture("adornment", 5)
                .essentia("citrinitas", 6));

        // The rings of second chance share luck and change, the leading materia is what they give a second time
        save(writer, distillation(ItemInit.SECOND_ATTACK_RING.get())
                .admixture("violence", 48).admixture("luck", 32).admixture("change", 16).admixture("adornment", 5)
                .essentia("citrinitas", 6));

        save(writer, distillation(ItemInit.SECOND_DROP_RING.get())
                .admixture("witchcraft", 48).admixture("luck", 32).admixture("change", 16).admixture("adornment", 5)
                .essentia("citrinitas", 6));

        save(writer, distillation(ItemInit.SECOND_PROTECTION_RING.get())
                .admixture("protection", 48).admixture("luck", 32).admixture("change", 16).admixture("adornment", 5)
                .essentia("citrinitas", 6));

        save(writer, distillation(ItemInit.SMITING_RING.get())
                .admixture("violence", 96).admixture("light", 48).admixture("firmament", 24).admixture("adornment", 10)
                .essentia("rubedo", 16));

        save(writer, distillation(ItemInit.BLACK_CAT_BRACELET.get())
                .admixture("disaster", 24).admixture("change", 16).admixture("violence", 8).admixture("adornment", 4)
                .essentia("albedo", 5));

        save(writer, distillation(ItemInit.BLOOD_LUST_BRACELET.get())
                .admixture("healing", 24).admixture("blood", 16).admixture("light", 8).admixture("adornment", 4)
                .essentia("albedo", 5));

        save(writer, distillation(ItemInit.TIME_BELT.get())
                .admixture("witchcraft", 40).admixture("permanence", 32).admixture("light", 16)
                .admixture("adornment", 5).essentia("citrinitas", 6));

        save(writer, distillation(ItemInit.BRIMSTONE_SWORD.get())
                .admixture("violence", 48).essentia("fire", 32).admixture("healing", 24).admixture("light", 12)
                .essentia("citrinitas", 6));

        save(writer, distillation(ItemInit.BLESSED_BOW.get())
                .admixture("violence", 80).admixture("light", 64).admixture("motion", 32).admixture("instrument", 8)
                .essentia("rubedo", 12));

        save(writer, distillation(ItemInit.WILD_PICKAXE.get())
                .admixture("instrument", 40).essentia("mineral", 32).admixture("change", 24).admixture("luck", 16)
                .essentia("citrinitas", 6));

        save(writer, distillation(ItemInit.SACRIFICIAL_DAGGER.get())
                .admixture("blood", 8).admixture("mana", 6).admixture("violence", 4).admixture("metal", 2)
                .admixture("instrument", 2));

        save(writer, distillation(ItemInit.DIVINE_HELMET.get())
                .admixture("protection", 40).admixture("light", 20).admixture("firmament", 20).essentia("rubedo", 12));

        save(writer, distillation(ItemInit.DIVINE_CHESTPLATE.get())
                .admixture("protection", 64).admixture("light", 32).admixture("firmament", 32).essentia("rubedo", 12));

        save(writer, distillation(ItemInit.DIVINE_LEGGING.get())
                .admixture("protection", 56).admixture("light", 28).admixture("firmament", 28).essentia("rubedo", 12));

        save(writer, distillation(ItemInit.DIVINE_BOOTS.get())
                .admixture("protection", 32).admixture("light", 16).admixture("firmament", 16).essentia("rubedo", 12));

        save(writer, distillation(ItemInit.MISCHIEF_HELMET.get())
                .admixture("luck", 40).admixture("change", 20).admixture("sorcery", 20).essentia("rubedo", 12));

        save(writer, distillation(ItemInit.MISCHIEF_CHESTPLATE.get())
                .admixture("luck", 64).admixture("change", 32).admixture("sorcery", 32).essentia("rubedo", 12));

        save(writer, distillation(ItemInit.MISCHIEF_LEGGING.get())
                .admixture("luck", 56).admixture("change", 28).admixture("sorcery", 28).essentia("rubedo", 12));

        save(writer, distillation(ItemInit.MISCHIEF_BOOTS.get())
                .admixture("luck", 32).admixture("change", 16).admixture("sorcery", 16).essentia("rubedo", 12));

        save(writer, distillation(ItemInit.DECK_OF_MANY.get())
                .admixture("change", 96).admixture("luck", 48).admixture("disaster", 48).admixture("instrument", 8)
                .essentia("rubedo", 16));

        // made six at a time, so like MagiChem's bulk items a larger yield with a lower rate
        save(writer, distillation(ItemInit.GLITTERING_POTATO.get())
                .outputRate(0.25f)
                .admixture("satiety", 6).admixture("healing", 4).admixture("luck", 4).admixture("destruction", 4));

        // duplication like MagiChem's Enderfeather Charm, so it still needs a potato of the Wild Courts
        save(writer, sublimation(ItemInit.GLITTERING_POTATO.get(), 3)
                .quantity(4)
                .stage(10)
                .items(ItemInit.GLITTERING_POTATO.get(), Items.BAKED_POTATO, Items.BAKED_POTATO, Items.BAKED_POTATO)
                .admixture("luck", 24).admixture("satiety", 24).admixture("healing", 16).admixture("destruction", 16));
    }
}
