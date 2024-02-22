package de.joh.fnc.common.init;

import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.common.enchantment.HolySmiteEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Init of all mod items.
 * @author Joh0210
 */
public class EnchantmentInit {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, FactionsAndCuriosities.MOD_ID);

    public static final RegistryObject<Enchantment> HOLY_SMITE = ENCHANTMENTS.register("holy_smite", () -> new HolySmiteEnchantment(Enchantment.Rarity.VERY_RARE));
    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
