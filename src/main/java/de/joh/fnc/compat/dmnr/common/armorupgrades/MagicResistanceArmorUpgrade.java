package de.joh.fnc.compat.dmnr.common.armorupgrades;

import de.joh.dragonmagicandrelics.armorupgrades.ArmorUpgradeInit;
import de.joh.dragonmagicandrelics.armorupgrades.types.ArmorUpgrade;
import de.joh.fnc.compat.dmnr.common.event.AddonDmnrDamageEventHandler;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Reduces the Magic Damage by 25%
 * @see AddonDmnrDamageEventHandler
 * @author Joh0210
 */
public class MagicResistanceArmorUpgrade extends ArmorUpgrade {
    public MagicResistanceArmorUpgrade(@NotNull ResourceLocation registryName, int maxUpgradeLevel, boolean isInfStackable, int upgradeCost) {
        super(registryName, maxUpgradeLevel, isInfStackable, upgradeCost);
    }

    @Override
    public @Nullable ArmorUpgrade getStrongerAlternative() {
        return ArmorUpgradeInit.DAMAGE_RESISTANCE;
    }
}
