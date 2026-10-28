package de.joh.fnc.common.init;

import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.common.item.*;
import de.joh.fnc.common.item.material.ToolMaterials;
import de.joh.tnl.api.item.CurioRegistry;
import de.joh.tnl.api.item.CurioSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;
import java.util.function.Supplier;

/**
 * Init of all mod items.
 * @author Joh0210
 */
public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FactionsAndCuriosities.MOD_ID);

    /**
     * Must be declared before the items, because it is filled while they are registered.
     */
    private static final CurioRegistry CURIOS = new CurioRegistry(ITEMS);

    public static final RegistryObject<Item> DEBUG_ROD = ITEMS.register("debug_orb_wild_magic", DebugOrbWildMagicItem::new);
    public static final RegistryObject<Item> DEBUG_ROD_SPELL_ADJUSTMENT = ITEMS.register("debug_orb_spell_adjustment", DebugOrbSpellAdjustmentItem::new);
    public static final RegistryObject<Item> DEBUG_ROD_SMITE = ITEMS.register("debug_orb_smite", DebugOrbSmiteItem::new);
    public static final RegistryObject<Item> SHIELD_ICON = ITEMS.register("shield_icon", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> DICE = ITEMS.register("dice_six", () -> new DiceItem(6));
    public static final RegistryObject<Item> DICE_20 = ITEMS.register("dice_twenty", () -> new DiceItem(20));
    public static final RegistryObject<Item> MISCHIEF_HELMET = ITEMS.register("mischief_helmet", () -> new MischiefArmorItem(ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> MISCHIEF_CHESTPLATE = ITEMS.register("mischief_chestplate", () -> new MischiefArmorItem(ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> MISCHIEF_LEGGING = ITEMS.register("mischief_legging", () -> new MischiefArmorItem(ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> MISCHIEF_BOOTS = ITEMS.register("mischief_boots", () -> new MischiefArmorItem(ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> BLACK_CAT_BRACELET = registerCurio("black_cat_bracelet", CurioSlot.BRACELET, BlackCatBraceletItem::new);
    public static final RegistryObject<Item> SECOND_PROTECTION_RING = registerCurio("second_protection_ring", CurioSlot.RING, () -> new SecondChanceItem(SecondChanceItem.Type.PROTECTION));
    public static final RegistryObject<Item> FRENZY_RING = registerCurio("frenzy_ring", CurioSlot.RING, () -> new FrenzyItem(false));
    public static final RegistryObject<Item> FRENZY_RING_MAJOR = registerCurio("frenzy_ring_greater", CurioSlot.RING, () -> new FrenzyItem(true));
    public static final RegistryObject<Item> HEAVY_WEAPON_RING = registerCurio("heavy_weapon_ring", CurioSlot.RING, () -> new HeavyWeaponRingItem(false));
    public static final RegistryObject<Item> HEAVY_WEAPON_RING_GREATER = registerCurio("heavy_weapon_ring_greater", CurioSlot.RING, () -> new HeavyWeaponRingItem(true));
    public static final RegistryObject<Item> TIME_BELT = registerCurio("time_belt", CurioSlot.BELT, TimeBeltItem::new);
    public static final RegistryObject<Item> AURA_FRAUD_RING = registerCurio("aura_fraud_ring", CurioSlot.RING, AuraFraudItem::new);
    public static final RegistryObject<Item> SECOND_ATTACK_RING = registerCurio("second_attack_ring", CurioSlot.RING, () -> new SecondChanceItem(SecondChanceItem.Type.ATTACK));
    public static final RegistryObject<Item> SECOND_DROP_RING = registerCurio("second_drop_ring", CurioSlot.RING, () -> new SecondChanceItem(SecondChanceItem.Type.POTION));
    public static final RegistryObject<Item> BLOOD_LUST_BRACELET = registerCurio("blood_lust_bracelet", CurioSlot.BRACELET, BloodLustBraceletItem::new);
    public static final RegistryObject<Item> SMITING_RING = registerCurio("smiting_ring", CurioSlot.RING, SmitingRingItem::new);
    public static final RegistryObject<Item> FOUR_LEAF_CLOVER_RING = registerCurio("four_leaf_clover_ring", CurioSlot.RING, FourLeafCloverRingItem::new);
    public static final RegistryObject<Item> DECK_OF_MANY = ITEMS.register("deck_of_many", DeckOfManyItem::new);
    public static final RegistryObject<Item> WILD_PICKAXE = ITEMS.register("wild_pickaxe", WildPickaxeItem::new);
    public static final RegistryObject<Item> RING_OF_WILD_LUCK = registerCurio("ring_of_wild_luck", CurioSlot.RING, RingOfWildLuckItem::new);
    public static final RegistryObject<Item> GLITTERING_POTATO = ITEMS.register("glittering_potato", GlitteringPotatoItem::new);
    public static final RegistryObject<Item> BRIMSTONE_SWORD = ITEMS.register("brimstone_sword", () -> new PaladinSwordItem(ToolMaterials.LIVE_RIP, 2, -2.4F, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()));
    public static final RegistryObject<Item> SACRIFICIAL_DAGGER = ITEMS.register("sacrificial_dagger", SacrificialDaggerItem::new);

    public static final RegistryObject<Item> DIVINE_HELMET = ITEMS.register("divine_helmet", () -> new DivineArmorItem(ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> DIVINE_CHESTPLATE = ITEMS.register("divine_chestplate", () -> new DivineArmorItem(ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> DIVINE_LEGGING = ITEMS.register("divine_legging", () -> new DivineArmorItem(ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> DIVINE_BOOTS = ITEMS.register("divine_boots", () -> new DivineArmorItem(ArmorItem.Type.BOOTS));


    public static final RegistryObject<Item> BLESSED_BOW = ITEMS.register("blessed_bow", BlessedBowItem::new);

    /**
     * Registers an item that can be equipped in the given Curios slot. The data generators add it to the item tag of
     * the slot, and they fail for every ICurioItem that is registered without a slot.
     */
    private static RegistryObject<Item> registerCurio(String name, CurioSlot slot, Supplier<? extends Item> item) {
        return CURIOS.register(name, slot, item);
    }

    /**
     * @return the Curios slot of every item that was registered with {@link #registerCurio}, in registration order
     */
    public static Map<RegistryObject<Item>, CurioSlot> getCurioSlots() {
        return CURIOS.getSlots();
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
