package de.joh.fnc.common.enchantment;

import de.joh.fnc.common.event.DamageEventHandler;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

/**
 * The Damage Type counts as Magical and pierces Armor
 * @see DamageEventHandler
 * @author Joh0210
 */
public class HolySmiteEnchantment extends Enchantment {
    public HolySmiteEnchantment(Rarity rarity) {
        super(rarity, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }
}
