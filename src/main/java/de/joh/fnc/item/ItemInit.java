package de.joh.fnc.item;

import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.item.init.DebugRod;
import de.joh.fnc.item.init.Dice;
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

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
