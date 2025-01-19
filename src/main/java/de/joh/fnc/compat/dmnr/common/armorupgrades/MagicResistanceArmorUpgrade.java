package de.joh.fnc.compat.dmnr.common.armorupgrades;

import de.joh.dmnr.api.armorupgrade.ArmorUpgrade;
import de.joh.dmnr.common.init.ArmorUpgradeInit;
import de.joh.fnc.compat.dmnr.common.event.AddonDmnrDamageEventHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.item.Item;

/**
 * Reduces the Magic Damage by 25%
 * @see AddonDmnrDamageEventHandler
 * @author Joh0210
 */
public class MagicResistanceArmorUpgrade extends ArmorUpgrade {
    public MagicResistanceArmorUpgrade(@NotNull ResourceLocation registryName, int maxUpgradeLevel, RegistryObject<Item> upgradeSealItem, boolean isInfStackable, int upgradeCost) {
        super(registryName, maxUpgradeLevel, upgradeSealItem, isInfStackable, upgradeCost);
    }

    @Override
    public @Nullable ArmorUpgrade getStrongerAlternative() {
        return ArmorUpgradeInit.DAMAGE_RESISTANCE;
    }
}
