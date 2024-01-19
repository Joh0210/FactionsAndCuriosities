package de.joh.fnc.compat.dmnr.common.init;

import de.joh.dmnr.common.item.UpgradeSealItem;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.common.util.RLoc;
import de.joh.fnc.compat.dmnr.common.item.DivineDragonMageArmorItem;
import de.joh.fnc.compat.dmnr.common.item.MischiefDragonMageArmorItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AddonDmnrItemInit
    {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FactionsAndCuriosities.MOD_ID);

        public static final RegistryObject<Item> MISCHIEF_DRAGON_MAGE_HELMET = ITEMS.register("mischief_dragon_mage_helmet", () -> new MischiefDragonMageArmorItem(ArmorItem.Type.HELMET));
        public static final RegistryObject<Item> MISCHIEF_DRAGON_MAGE_CHESTPLATE = ITEMS.register("mischief_dragon_mage_chestplate", () -> new MischiefDragonMageArmorItem(ArmorItem.Type.CHESTPLATE));
        public static final RegistryObject<Item> MISCHIEF_DRAGON_MAGE_LEGGING = ITEMS.register("mischief_dragon_mage_leggings", () -> new MischiefDragonMageArmorItem(ArmorItem.Type.LEGGINGS));
        public static final RegistryObject<Item> MISCHIEF_DRAGON_MAGE_BOOTS = ITEMS.register("mischief_dragon_mage_boots", () -> new MischiefDragonMageArmorItem(ArmorItem.Type.BOOTS));
        public static final RegistryObject<Item> DIVINE_DRAGON_MAGE_HELMET = ITEMS.register("divine_dragon_mage_helmet", () -> new DivineDragonMageArmorItem(ArmorItem.Type.HELMET));
        public static final RegistryObject<Item> DIVINE_DRAGON_MAGE_CHESTPLATE = ITEMS.register("divine_dragon_mage_chestplate", () -> new DivineDragonMageArmorItem(ArmorItem.Type.CHESTPLATE));
        public static final RegistryObject<Item> DIVINE_DRAGON_MAGE_LEGGING = ITEMS.register("divine_dragon_mage_leggings", () -> new DivineDragonMageArmorItem(ArmorItem.Type.LEGGINGS));
        public static final RegistryObject<Item> DIVINE_DRAGON_MAGE_BOOTS = ITEMS.register("divine_dragon_mage_boots", () -> new DivineDragonMageArmorItem(ArmorItem.Type.BOOTS));

        public static final RegistryObject<Item> UPGRADE_SEAL_WILD_MAGIC_LUCK = ITEMS.register("upgrade_seal_wild_magic_luck", () -> new UpgradeSealItem(RLoc.create("armorupgrade/wild_magic_luck")));
        public static final RegistryObject<Item> UPGRADE_SEAL_RANDOM_SPELL_ADJUSTMENT = ITEMS.register("upgrade_seal_random_spell_adjustment", () -> new UpgradeSealItem(RLoc.create("armorupgrade/random_spell_adjustment")));
        public static final RegistryObject<Item> UPGRADE_SEAL_MAGIC_RESISTANCE = ITEMS.register("upgrade_seal_magic_resistance", () -> new UpgradeSealItem(RLoc.create("armorupgrade/magic_resistance")));
        public static final RegistryObject<Item> UPGRADE_SEAL_SMITE_DURATION = ITEMS.register("upgrade_seal_smite_duration", () -> new UpgradeSealItem(RLoc.create("armorupgrade/smite_duration")));
    }
