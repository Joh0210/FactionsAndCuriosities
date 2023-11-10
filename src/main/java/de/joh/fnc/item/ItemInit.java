package de.joh.fnc.item;

import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.item.init.*;
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

    public static final RegistryObject<Item> DEBUG_ROD = ITEMS.register("debug_orb_wild_magic", DebugOrbWildMagic::new);
    public static final RegistryObject<Item> DEBUG_ROD_SPELL_ADJUSTMENT = ITEMS.register("debug_orb_spell_adjustment", DebugOrbSpellAdjustment::new);
    public static final RegistryObject<Item> DICE = ITEMS.register("dice_six", () -> new Dice(6));
    public static final RegistryObject<Item> DICE_20 = ITEMS.register("dice_twenty", () -> new Dice(20));

    public static final RegistryObject<Item> MISCHIEF_HELMET = ITEMS.register("mischief_helmet", () -> new MischiefArmor(EquipmentSlot.HEAD));
    public static final RegistryObject<Item> MISCHIEF_CHESTPLATE = ITEMS.register("mischief_chestplate", () -> new MischiefArmor(EquipmentSlot.CHEST));
    public static final RegistryObject<Item> MISCHIEF_LEGGING = ITEMS.register("mischief_legging", () -> new MischiefArmor(EquipmentSlot.LEGS));
    public static final RegistryObject<Item> MISCHIEF_BOOTS = ITEMS.register("mischief_boots", () -> new MischiefArmor(EquipmentSlot.FEET));
    public static final RegistryObject<Item> BLACK_CAT_BRACELET = ITEMS.register("black_cat_bracelet", BlackCatBracelet::new);
    public static final RegistryObject<Item> FOUR_LEAF_CLOVER_RING = ITEMS.register("four_leaf_clover_ring", FourLeafCloverRing::new);
    public static final RegistryObject<Item> DECK_OF_MANY = ITEMS.register("deck_of_many", DeckOfMany::new);
    public static final RegistryObject<Item> WILD_PICKAXE = ITEMS.register("wild_pickaxe", WildPickaxe::new);
    public static final RegistryObject<Item> GLITTERING_POTATO = ITEMS.register("glittering_potato", GlitteringPotato::new);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
