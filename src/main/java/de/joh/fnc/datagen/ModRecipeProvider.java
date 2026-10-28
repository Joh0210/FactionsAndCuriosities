package de.joh.fnc.datagen;

import com.mna.api.affinity.Affinity;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.common.faction.ResourceIDs;
import de.joh.fnc.common.init.ItemInit;
import de.joh.fnc.common.init.SpellInit;
import de.joh.tnl.api.datagen.MnaRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

import static com.mna.items.ItemInit.*;
import static de.joh.tnl.api.datagen.EldrinAltarRecipeBuilder.eldrinAltar;
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
    private static final TagKey<Item> MA_FLOWERS = ItemTags.create(new ResourceLocation("mna", "ma_flowers"));

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
    }

    private void materials(Consumer<FinishedRecipe> writer) {
        save(writer, "dice_six", manaweaving(ItemInit.DICE.get(), 2)
                .item(Items.OBSIDIAN, 3)
                .items(MOTE_AIR.get(), RITUAL_FOCUS_LESSER.get(), MOTE_ARCANE.get())
                .item(Items.GOLD_INGOT, 3)
                .patterns(SQUARE, TRIANGLE, SQUARE));

        save(writer, "dice_twenty", eldrinAltar(ItemInit.DICE_20.get(), 4)
                .items(ItemInit.DICE.get())
                .item(Items.OBSIDIAN, 3)
                .items(GREATER_MOTE_AIR.get(), GREATER_MOTE_ARCANE.get())
                .item(Items.GOLD_BLOCK, 3)
                .power(500, Affinity.WIND, Affinity.ARCANE));
    }

    private void rings(Consumer<FinishedRecipe> writer) {
        save(writer, "aura_fraud_ring", manaweaving(ItemInit.AURA_FRAUD_RING.get(), 3)
                .requiredFaction(ResourceIDs.FACTION_PALADIN_ID)
                .items(MUNDANE_RING.get(), Items.IRON_BLOCK, RUNE_AURA.get(),
                        com.mna.blocks.BlockInit.CHIMERITE_CRYSTAL_CYAN.get(), MOTE_ARCANE.get(), INFUSED_THREAD.get())
                .patterns(DIAMOND));

        save(writer, "four_leaf_clover_ring", manaweaving(ItemInit.FOUR_LEAF_CLOVER_RING.get(), 3)
                .requiredFaction(ResourceIDs.FACTION_WILD_ID)
                .items(ItemInit.DICE.get(), MANA_CRYSTAL_FRAGMENT.get(), MUNDANE_RING.get())
                .tag(ItemTags.LEAVES)
                .tag(ItemTags.LEAVES)
                .tag(ItemTags.LEAVES)
                .tag(ItemTags.LEAVES)
                .tag(MA_FLOWERS)
                .tag(MA_FLOWERS)
                .patterns(CIRCLE, CIRCLE, CIRCLE));

        save(writer, "frenzy_ring", manaweaving(ItemInit.FRENZY_RING.get(), 3)
                .requiredFaction(ResourceIDs.FACTION_PALADIN_ID)
                .items(MUNDANE_RING.get(), Items.GHAST_TEAR, Items.NETHER_WART_BLOCK, Items.CHAIN, Items.NETHER_WART_BLOCK,
                        Items.CHAIN)
                .patterns(SQUARE, KNOT4, TRIANGLE));

        save(writer, "frenzy_ring_greater", eldrinAltar(ItemInit.FRENZY_RING_MAJOR.get(), 5)
                .requiredFaction(ResourceIDs.FACTION_PALADIN_ID)
                .items(ItemInit.FRENZY_RING.get(), RUNE_ARCANE.get(), Items.NETHERITE_INGOT, RUNE_ENDER.get(),
                        Items.NETHER_STAR)
                .power(750, Affinity.ENDER, Affinity.ARCANE));

        save(writer, "heavy_weapon_ring", manaweaving(ItemInit.HEAVY_WEAPON_RING.get(), 3)
                .requiredFaction(ResourceIDs.FACTION_PALADIN_ID)
                .items(MUNDANE_RING.get(), Items.IRON_BLOCK, Items.IRON_SWORD, Items.IRON_BLOCK, Items.IRON_SWORD,
                        Items.IRON_BLOCK, Items.IRON_SWORD, Items.IRON_BLOCK, Items.IRON_SWORD)
                .patterns(SQUARE, DIAMOND, SQUARE));

        save(writer, "heavy_weapon_ring_greater", eldrinAltar(ItemInit.HEAVY_WEAPON_RING_GREATER.get(), 5)
                .requiredFaction(ResourceIDs.FACTION_PALADIN_ID)
                .items(ItemInit.HEAVY_WEAPON_RING.get(), GREATER_MOTE_EARTH.get(), Items.NETHERITE_SWORD,
                        STONE_RUNE_CYAN.get(), Items.NETHERITE_SWORD, RUNE_EARTH.get(), Items.NETHERITE_SWORD,
                        STONE_RUNE_WHITE.get(), Items.NETHERITE_SWORD)
                .power(1200, Affinity.EARTH)
                .power(300, Affinity.ARCANE));

        save(writer, "ring_of_wild_luck", manaweaving(ItemInit.RING_OF_WILD_LUCK.get(), 4)
                .requiredFaction(ResourceIDs.FACTION_WILD_ID)
                .items(ItemInit.DICE_20.get(), MUNDANE_RING.get(), Items.GOLD_BLOCK, Items.NETHERITE_INGOT,
                        Items.GOLD_BLOCK, Items.NETHERITE_INGOT, Items.GOLD_BLOCK)
                .patterns(CIRCLE, SQUARE, SPLIT_TRIANGLE));

        save(writer, "second_attack_ring", manaweaving(ItemInit.SECOND_ATTACK_RING.get(), 4)
                .requiredFaction(ResourceIDs.FACTION_WILD_ID)
                .items(ItemInit.DICE_20.get(), MUNDANE_RING.get(), Items.YELLOW_CANDLE, BRIMSTONE_CHARM.get())
                .patterns(KNOT3, KNOT4, KNOT3));

        save(writer, "second_drop_ring", manaweaving(ItemInit.SECOND_DROP_RING.get(), 4)
                .requiredFaction(ResourceIDs.FACTION_WILD_ID)
                .items(ItemInit.DICE_20.get(), MUNDANE_RING.get(), Items.GREEN_CANDLE, BRIMSTONE_CHARM.get())
                .patterns(KNOT, KNOT4, KNOT2));

        save(writer, "second_protection_ring", manaweaving(ItemInit.SECOND_PROTECTION_RING.get(), 4)
                .requiredFaction(ResourceIDs.FACTION_WILD_ID)
                .items(ItemInit.DICE_20.get(), MUNDANE_RING.get(), Items.BLACK_CANDLE, BRIMSTONE_CHARM.get())
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
                .items(ItemInit.DICE.get(), MANA_CRYSTAL_FRAGMENT.get(), MUNDANE_BRACELET.get())
                .item(Items.BLACK_DYE, 2)
                .patterns(TRIANGLE, CIRCLE, SPLIT_TRIANGLE, SQUARE, DIAMOND, DIAMOND));

        save(writer, "blood_lust_bracelet", manaweaving(ItemInit.BLOOD_LUST_BRACELET.get(), 3)
                .requiredFaction(ResourceIDs.FACTION_PALADIN_ID)
                .items(Items.GHAST_TEAR, Items.GOLDEN_APPLE, MANA_CRYSTAL_FRAGMENT.get(), MUNDANE_BRACELET.get(),
                        Items.DIAMOND_SWORD, STONE_RUNE_RED.get())
                .patterns(SQUARE, TRIANGLE, SQUARE));

        save(writer, "time_belt", manaweaving(ItemInit.TIME_BELT.get(), 4)
                .requiredFaction(ResourceIDs.FACTION_PALADIN_ID)
                .items(BELT_BUCKLE.get(), Items.CHAIN, Items.CLOCK, GREATER_MOTE_ARCANE.get(), STONE_RUNE_YELLOW.get(),
                        RITUAL_FOCUS_GREATER.get(), Items.NETHER_WART_BLOCK, Items.REDSTONE, Items.GLOWSTONE_DUST)
                .patterns(HOURGLASS, HOURGLASS, HOURGLASS));
    }

    private void weaponsAndTools(Consumer<FinishedRecipe> writer) {
        save(writer, "brimstone_sword", eldrinAltar(ItemInit.BRIMSTONE_SWORD.get(), 4)
                .requiredFaction(ResourceIDs.FACTION_PALADIN_ID)
                .items(Items.NETHERITE_SWORD, BRIMSTONE_CHARM.get(), Items.GHAST_TEAR)
                .power(700, Affinity.FIRE)
                .power(500, Affinity.EARTH, Affinity.ENDER));

        save(writer, "blessed_bow", eldrinAltar(ItemInit.BLESSED_BOW.get(), 5)
                .requiredFaction(ResourceIDs.FACTION_PALADIN_ID)
                .items(Items.BOW, Items.NETHERITE_SWORD, Items.GHAST_TEAR, Items.NETHERITE_SWORD, Items.NETHER_STAR,
                        Items.NETHERITE_SWORD, Items.WITHER_SKELETON_SKULL, Items.NETHERITE_SWORD, Items.BLAZE_ROD)
                .power(1000, Affinity.ARCANE, Affinity.EARTH));

        save(writer, "wild_pickaxe", manaweaving(ItemInit.WILD_PICKAXE.get(), 4)
                .requiredFaction(ResourceIDs.FACTION_WILD_ID)
                .items(ItemInit.DICE.get(), Items.GOLDEN_PICKAXE, Items.COPPER_INGOT, Items.IRON_INGOT,
                        Items.GOLD_INGOT, Items.COAL, Items.LAPIS_LAZULI, Items.DIAMOND, Items.EMERALD)
                .patterns(SPLIT_TRIANGLE, TRIANGLE, CIRCLE, INVERTED_TRIANGLE, SQUARE, DIAMOND));
    }

    private void armor(Consumer<FinishedRecipe> writer) {
        save(writer, "divine_helmet", eldrinAltar(ItemInit.DIVINE_HELMET.get(), 5)
                .requiredFaction(ResourceIDs.FACTION_PALADIN_ID)
                .items(Items.NETHERITE_HELMET, GREATER_MOTE_EARTH.get(), GREATER_MOTE_ARCANE.get())
                .item(RUNE_PATTERN_DEFENSE.get(), 2)
                .items(Items.DIAMOND_BLOCK)
                .item(Items.GOLD_BLOCK, 3)
                .power(750, Affinity.ARCANE)
                .power(200, Affinity.EARTH));

        save(writer, "divine_chestplate", eldrinAltar(ItemInit.DIVINE_CHESTPLATE.get(), 5)
                .requiredFaction(ResourceIDs.FACTION_PALADIN_ID)
                .items(Items.NETHERITE_CHESTPLATE, GREATER_MOTE_EARTH.get(), GREATER_MOTE_ARCANE.get())
                .item(RUNE_PATTERN_DEFENSE.get(), 2)
                .items(Items.DIAMOND_BLOCK)
                .item(Items.GOLD_BLOCK, 3)
                .power(1500, Affinity.ARCANE));

        save(writer, "divine_legging", eldrinAltar(ItemInit.DIVINE_LEGGING.get(), 5)
                .requiredFaction(ResourceIDs.FACTION_PALADIN_ID)
                .items(Items.NETHERITE_LEGGINGS, GREATER_MOTE_EARTH.get(), GREATER_MOTE_ARCANE.get())
                .item(RUNE_PATTERN_DEFENSE.get(), 2)
                .items(Items.DIAMOND_BLOCK)
                .item(Items.GOLD_BLOCK, 3)
                .power(750, Affinity.ARCANE)
                .power(200, Affinity.EARTH));

        save(writer, "divine_boots", eldrinAltar(ItemInit.DIVINE_BOOTS.get(), 5)
                .requiredFaction(ResourceIDs.FACTION_PALADIN_ID)
                .items(Items.NETHERITE_BOOTS, GREATER_MOTE_EARTH.get(), GREATER_MOTE_ARCANE.get())
                .item(RUNE_PATTERN_DEFENSE.get(), 2)
                .items(Items.DIAMOND_BLOCK)
                .item(Items.GOLD_BLOCK, 3)
                .power(750, Affinity.ARCANE)
                .power(200, Affinity.EARTH));

        save(writer, "mischief_helmet", eldrinAltar(ItemInit.MISCHIEF_HELMET.get(), 5)
                .requiredFaction(ResourceIDs.FACTION_WILD_ID)
                .items(Items.GOLDEN_HELMET, ItemInit.DICE_20.get(), RUNE_ARCANE.get(), RUNE_AIR.get(), RUNIC_SILK.get(),
                        INFUSED_THREAD.get())
                .power(750, Affinity.ARCANE)
                .power(200, Affinity.WIND));

        save(writer, "mischief_chestplate", eldrinAltar(ItemInit.MISCHIEF_CHESTPLATE.get(), 5)
                .requiredFaction(ResourceIDs.FACTION_WILD_ID)
                .items(Items.GOLDEN_CHESTPLATE, ItemInit.DICE_20.get(), RUNE_ARCANE.get(), RUNE_AIR.get(),
                        RUNIC_SILK.get(), INFUSED_THREAD.get())
                .item(Items.GREEN_WOOL, 2)
                .power(1500, Affinity.ARCANE));

        save(writer, "mischief_legging", eldrinAltar(ItemInit.MISCHIEF_LEGGING.get(), 5)
                .requiredFaction(ResourceIDs.FACTION_WILD_ID)
                .items(Items.GOLDEN_LEGGINGS, ItemInit.DICE_20.get(), RUNE_ARCANE.get(), RUNE_ENDER.get(),
                        RUNIC_SILK.get(), INFUSED_THREAD.get(), Items.GREEN_WOOL)
                .power(750, Affinity.ARCANE)
                .power(200, Affinity.ENDER));

        save(writer, "mischief_boots", eldrinAltar(ItemInit.MISCHIEF_BOOTS.get(), 5)
                .requiredFaction(ResourceIDs.FACTION_WILD_ID)
                .items(Items.GOLDEN_BOOTS, ItemInit.DICE_20.get(), RUNE_ARCANE.get(), RUNE_AIR.get(), RUNIC_SILK.get(),
                        INFUSED_THREAD.get())
                .power(750, Affinity.ARCANE)
                .power(200, Affinity.WIND));
    }

    private void miscellaneous(Consumer<FinishedRecipe> writer) {
        save(writer, "deck_of_many", eldrinAltar(ItemInit.DECK_OF_MANY.get(), 5)
                .requiredFaction(ResourceIDs.FACTION_WILD_ID)
                .items(Items.PAPER, ItemInit.DICE_20.get(), Items.NETHER_STAR, Items.TNT, Items.DIAMOND_BLOCK,
                        Items.ENDER_PEARL, RUNE_AIR.get(), RUNE_ARCANE.get(), RUNE_ENDER.get())
                .power(150, Affinity.WIND)
                .power(500, Affinity.ARCANE)
                .power(350, Affinity.ENDER));

        save(writer, "glittering_potato", manaweaving(ItemInit.GLITTERING_POTATO.get(), 3)
                .quantity(8)
                .requiredFaction(ResourceIDs.FACTION_WILD_ID)
                .items(CHIMERITE_GEM.get(), Items.POISONOUS_POTATO)
                .item(Items.BAKED_POTATO, 7)
                .patterns(CIRCLE));
    }

    private void components(Consumer<FinishedRecipe> writer) {
        save(writer, component(SpellInit.BLOOD_SACRIFICE, 3)
                .items(Items.GLASS_BOTTLE, Items.FLINT, Items.ROTTEN_FLESH, Items.REDSTONE)
                .patterns(TRIANGLE, INVERTED_TRIANGLE));

        save(writer, component(SpellInit.BOOMING_SMITE, 4)
                .items(Items.DIAMOND_SWORD, Items.GUNPOWDER, Items.TNT, MOTE_FIRE.get(),
                        com.mna.blocks.BlockInit.DESERT_NOVA.get())
                .patterns(CIRCLE, DIAMOND, SQUARE));

        save(writer, component(SpellInit.BURNING_SMITE, 3)
                .items(Items.IRON_SWORD, STONE_RUNE_ORANGE.get(), Items.FLINT_AND_STEEL)
                .patterns(TRIANGLE));

        save(writer, component(SpellInit.CAUSE_WILD_MAGIC, 5)
                .items(ItemInit.BLACK_CAT_BRACELET.get(), Items.ENDER_EYE, Items.ENDER_EYE, Items.NETHER_STAR)
                .patterns(STAR, INFINITY, HOURGLASS));

        save(writer, component(SpellInit.CRIPPLING_SMITE, 3)
                .items(Items.DIAMOND_SWORD, Items.ANVIL, Items.SPIDER_EYE)
                .patterns(INVERTED_TRIANGLE, SQUARE));

        save(writer, component(SpellInit.DISPELLING_SMITE, 4)
                .items(Items.DIAMOND_SWORD, Items.GLASS_BOTTLE, Items.MILK_BUCKET, Items.GHAST_TEAR)
                .patterns(INVERTED_TRIANGLE, HOURGLASS, CIRCLE));

        save(writer, component(SpellInit.FINAL_DUAL, 4)
                .items(Items.NETHERITE_INGOT, Items.WITHER_SKELETON_SKULL, Items.TOTEM_OF_UNDYING)
                .patterns(TRIANGLE, INVERTED_TRIANGLE, CIRCLE, SQUARE));

        save(writer, component(SpellInit.GAMBLERS_BLOW_DEFENSIVE, 4)
                .items(ItemInit.DICE.get(), Items.GHAST_TEAR, ARCANE_ASH.get(), Items.SHIELD)
                .patterns(INVERTED_TRIANGLE, HOURGLASS, INVERTED_TRIANGLE));

        save(writer, component(SpellInit.GAMBLERS_BLOW_OFFENSIVE, 4)
                .items(ItemInit.DICE.get(), Items.GHAST_TEAR, ARCANE_ASH.get(), Items.IRON_SWORD)
                .patterns(TRIANGLE, HOURGLASS, TRIANGLE));

        save(writer, component(SpellInit.HEX, 4)
                .items(Items.WITHER_ROSE, VINTEUM_DUST.get(), Items.ROTTEN_FLESH, Items.GHAST_TEAR)
                .patterns(INVERTED_TRIANGLE, HOURGLASS));

        save(writer, component(SpellInit.HEXING_SMITE, 4)
                .items(Items.DIAMOND_SWORD, Items.WITHER_ROSE, VINTEUM_DUST.get(), Items.ROTTEN_FLESH)
                .patterns(INVERTED_TRIANGLE, HOURGLASS));

        save(writer, component(SpellInit.POSTPONEMENT, 5)
                .items(Items.CLOCK, Items.NETHER_STAR, Items.DRAGON_BREATH)
                .patterns(HOURGLASS, INFINITY, HOURGLASS, INFINITY));

        save(writer, component(SpellInit.RANDOM_TP, 3)
                .items(VINTEUM_DUST.get(), VINTEUM_DUST.get(), Items.CHORUS_FRUIT, MOTE_ENDER.get())
                .patterns(INVERTED_TRIANGLE, SQUARE));

        save(writer, component(SpellInit.SACRIFICIAL_LAMB, 4)
                .items(Items.MUTTON, Items.FLINT, Items.GLASS_BOTTLE, Items.REDSTONE)
                .patterns(TRIANGLE, INVERTED_TRIANGLE, CIRCLE));

        save(writer, component(SpellInit.SILENCING_SMITE, 5)
                .items(Items.NETHERITE_SWORD)
                .tag(ItemTags.WOOL)
                .tag(ItemTags.WOOL)
                .items(com.mna.blocks.BlockInit.AUM.get(), ARCANE_ASH.get())
                .patterns(INFINITY, SPLIT_TRIANGLE, DIAMOND));

        save(writer, component(SpellInit.WILD_DAMAGE, 3)
                .items(ItemInit.DICE.get())
                .item(Items.GOLDEN_SWORD, 3)
                .patterns(TRIANGLE, KNOT, KNOT3, CIRCLE));

        save(writer, component(SpellInit.WILD_MAGIC_COOLDOWN, 3)
                .items(ItemInit.DICE.get(), Items.CLOCK, Items.REPEATER)
                .patterns(CIRCLE, CIRCLE));
    }

    private void shapes(Consumer<FinishedRecipe> writer) {
        save(writer, shape(SpellInit.COUNTER_ATTACK, 5)
                .items(Items.SHIELD, Items.NETHERITE_INGOT, GREATER_MOTE_ARCANE.get(), Items.NETHERITE_INGOT,
                        GREATER_MOTE_ENDER.get())
                .patterns(SPLIT_TRIANGLE, INFINITY, HOURGLASS, INFINITY, INVERTED_SPLIT_TRIANGLE));

        save(writer, shape(SpellInit.SMITE, 5)
                .items(Items.NETHERITE_SWORD, GREATER_MOTE_AIR.get(), GREATER_MOTE_EARTH.get(),
                        GREATER_MOTE_WATER.get(), GREATER_MOTE_FIRE.get(), GREATER_MOTE_ARCANE.get(),
                        GREATER_MOTE_ENDER.get())
                .patterns(INFINITY, SPLIT_TRIANGLE, CIRCLE, HOURGLASS, INVERTED_SPLIT_TRIANGLE, INFINITY));
    }
}
