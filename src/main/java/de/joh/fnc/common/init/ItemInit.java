package de.joh.fnc.common.init;

import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.common.item.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Init of all mod items.
 * @author Joh0210
 */
public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FactionsAndCuriosities.MOD_ID);

    public static final RegistryObject<Item> DEBUG_ROD = ITEMS.register("debug_orb_wild_magic", DebugOrbWildMagicItem::new);
    public static final RegistryObject<Item> DEBUG_ROD_SPELL_ADJUSTMENT = ITEMS.register("debug_orb_spell_adjustment", DebugOrbSpellAdjustmentItem::new);
    public static final RegistryObject<Item> DEBUG_ROD_SMITE = ITEMS.register("debug_orb_smite", DebugOrbSmiteItem::new);
    public static final RegistryObject<Item> DICE = ITEMS.register("dice_six", () -> new DiceItem(6));
    public static final RegistryObject<Item> DICE_20 = ITEMS.register("dice_twenty", () -> new DiceItem(20));
    public static final RegistryObject<Item> MISCHIEF_HELMET = ITEMS.register("mischief_helmet", () -> new MischiefArmorItem(EquipmentSlot.HEAD));
    public static final RegistryObject<Item> MISCHIEF_CHESTPLATE = ITEMS.register("mischief_chestplate", () -> new MischiefArmorItem(EquipmentSlot.CHEST));
    public static final RegistryObject<Item> MISCHIEF_LEGGING = ITEMS.register("mischief_legging", () -> new MischiefArmorItem(EquipmentSlot.LEGS));
    public static final RegistryObject<Item> MISCHIEF_BOOTS = ITEMS.register("mischief_boots", () -> new MischiefArmorItem(EquipmentSlot.FEET));
    public static final RegistryObject<Item> BLACK_CAT_BRACELET = ITEMS.register("black_cat_bracelet", BlackCatBraceletItem::new);
    public static final RegistryObject<Item> BLOOD_LUST_BRACELET = ITEMS.register("blood_lust_bracelet", BloodLustBraceletItem::new);
    public static final RegistryObject<Item> SMITING_RING = ITEMS.register("smiting_ring", SmitingRingItem::new);
    public static final RegistryObject<Item> FOUR_LEAF_CLOVER_RING = ITEMS.register("four_leaf_clover_ring", FourLeafCloverRingItem::new);
    public static final RegistryObject<Item> DECK_OF_MANY = ITEMS.register("deck_of_many", DeckOfManyItem::new);
    public static final RegistryObject<Item> WILD_PICKAXE = ITEMS.register("wild_pickaxe", WildPickaxeItem::new);
    public static final RegistryObject<Item> GLITTERING_POTATO = ITEMS.register("glittering_potato", GlitteringPotatoItem::new);
    public static final RegistryObject<Item> BRIMSTONE_SWORD = ITEMS.register("brimstone_sword", BrimstoneSwordItem::new);

    public static final RegistryObject<Item> DIVINE_HELMET = ITEMS.register("divine_helmet", () -> new DivineArmorItem(EquipmentSlot.HEAD));
    public static final RegistryObject<Item> DIVINE_CHESTPLATE = ITEMS.register("divine_chestplate", () -> new DivineArmorItem(EquipmentSlot.CHEST));
    public static final RegistryObject<Item> DIVINE_LEGGING = ITEMS.register("divine_legging", () -> new DivineArmorItem(EquipmentSlot.LEGS));
    public static final RegistryObject<Item> DIVINE_BOOTS = ITEMS.register("divine_boots", () -> new DivineArmorItem(EquipmentSlot.FEET));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
