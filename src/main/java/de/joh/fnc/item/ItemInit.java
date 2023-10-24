package de.joh.fnc.item;

import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.item.init.DebugRod;
import de.joh.fnc.item.init.Dice;
import de.joh.fnc.item.init.MischiefArmor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Inits of all mod items.
 * @author Joh0210
 */
public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FactionsAndCuriosities.MOD_ID);

    public static final RegistryObject<Item> DEBUG_ROD = ITEMS.register("debug_rod", DebugRod::new);
    public static final RegistryObject<Item> DICE = ITEMS.register("dice_six", () -> new Dice(6));

    public static final RegistryObject<Item> MISCHIEF_HELMET = ITEMS.register("mischief_helmet", () -> new MischiefArmor(EquipmentSlot.HEAD));
    public static final RegistryObject<Item> MISCHIEF_CHESTPLATE = ITEMS.register("mischief_chestplate", () -> new MischiefArmor(EquipmentSlot.CHEST));
    public static final RegistryObject<Item> MISCHIEF_LEGGING = ITEMS.register("mischief_legging", () -> new MischiefArmor(EquipmentSlot.LEGS));
    public static final RegistryObject<Item> MISCHIEF_BOOTS = ITEMS.register("mischief_boots", () -> new MischiefArmor(EquipmentSlot.FEET));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
