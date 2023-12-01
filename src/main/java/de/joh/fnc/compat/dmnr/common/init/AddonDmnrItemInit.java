package de.joh.fnc.compat.dmnr.common.init;

import de.joh.dragonmagicandrelics.item.items.UpgradeSeal;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.common.util.RLoc;
import de.joh.fnc.compat.dmnr.common.item.DivineDragonMageArmorItem;
import de.joh.fnc.compat.dmnr.common.item.MischiefDragonMageArmorItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AddonDmnrItemInit
    {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FactionsAndCuriosities.MOD_ID);

        public static final RegistryObject<Item> MISCHIEF_DRAGON_MAGE_HELMET = ITEMS.register("mischief_dragon_mage_helmet", () -> new MischiefDragonMageArmorItem(EquipmentSlot.HEAD));
        public static final RegistryObject<Item> MISCHIEF_DRAGON_MAGE_CHESTPLATE = ITEMS.register("mischief_dragon_mage_chestplate", () -> new MischiefDragonMageArmorItem(EquipmentSlot.CHEST));
        public static final RegistryObject<Item> MISCHIEF_DRAGON_MAGE_LEGGING = ITEMS.register("mischief_dragon_mage_leggings", () -> new MischiefDragonMageArmorItem(EquipmentSlot.LEGS));
        public static final RegistryObject<Item> MISCHIEF_DRAGON_MAGE_BOOTS = ITEMS.register("mischief_dragon_mage_boots", () -> new MischiefDragonMageArmorItem(EquipmentSlot.FEET));
        public static final RegistryObject<Item> DIVINE_DRAGON_MAGE_HELMET = ITEMS.register("divine_dragon_mage_helmet", () -> new DivineDragonMageArmorItem(EquipmentSlot.HEAD));
        public static final RegistryObject<Item> DIVINE_DRAGON_MAGE_CHESTPLATE = ITEMS.register("divine_dragon_mage_chestplate", () -> new DivineDragonMageArmorItem(EquipmentSlot.CHEST));
        public static final RegistryObject<Item> DIVINE_DRAGON_MAGE_LEGGING = ITEMS.register("divine_dragon_mage_leggings", () -> new DivineDragonMageArmorItem(EquipmentSlot.LEGS));
        public static final RegistryObject<Item> DIVINE_DRAGON_MAGE_BOOTS = ITEMS.register("divine_dragon_mage_boots", () -> new DivineDragonMageArmorItem(EquipmentSlot.FEET));

        public static final RegistryObject<Item> UPGRADE_SEAL_WILD_MAGIC_LUCK = ITEMS.register("upgrade_seal_wild_magic_luck", () -> new UpgradeSeal(RLoc.create("armorupgrade/wild_magic_luck")));
        public static final RegistryObject<Item> UPGRADE_SEAL_RANDOM_SPELL_ADJUSTMENT = ITEMS.register("upgrade_seal_random_spell_adjustment", () -> new UpgradeSeal(RLoc.create("armorupgrade/random_spell_adjustment")));
    }
