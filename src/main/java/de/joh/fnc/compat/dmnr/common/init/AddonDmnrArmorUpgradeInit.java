package de.joh.fnc.compat.dmnr.common.init;

import de.joh.dragonmagicandrelics.armorupgrades.types.ArmorUpgrade;
import de.joh.dragonmagicandrelics.item.items.dragonmagearmor.DragonMageArmor;
import de.joh.fnc.common.util.RLoc;
import de.joh.fnc.compat.dmnr.common.armorupgrades.MagicResistanceArmorUpgrade;
import de.joh.fnc.compat.dmnr.common.armorupgrades.WildMagicLuckArmorUpgrade;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * An initialization of the F&C upgrades for the Dragon Mage Armor.
 * @see DragonMageArmor
 * @author Joh0210
 */
public class AddonDmnrArmorUpgradeInit {
    public static ArmorUpgrade WILD_MAGIC_LUCK;
    public static ArmorUpgrade RANDOM_SPELL_ADJUSTMENT;
    public static ArmorUpgrade MAGIC_RESISTANCE;
    /**
     * Increases the Smite Duration by 50%
     */
    public static ArmorUpgrade SMITE_DURATION;

    @SubscribeEvent
    public static void registerArmorUpgrades(final RegistryEvent.Register<ArmorUpgrade> event) {
        event.getRegistry().register(AddonDmnrArmorUpgradeInit.WILD_MAGIC_LUCK);
        event.getRegistry().register(AddonDmnrArmorUpgradeInit.RANDOM_SPELL_ADJUSTMENT);
        event.getRegistry().register(AddonDmnrArmorUpgradeInit.MAGIC_RESISTANCE);
        event.getRegistry().register(AddonDmnrArmorUpgradeInit.SMITE_DURATION);
    }

    static {
        AddonDmnrArmorUpgradeInit.WILD_MAGIC_LUCK = new WildMagicLuckArmorUpgrade(RLoc.create("armorupgrade/wild_magic_luck"), 7);
        AddonDmnrArmorUpgradeInit.RANDOM_SPELL_ADJUSTMENT = new ArmorUpgrade(RLoc.create("armorupgrade/random_spell_adjustment"), 2, true, 4);
        AddonDmnrArmorUpgradeInit.MAGIC_RESISTANCE = new MagicResistanceArmorUpgrade(RLoc.create("armorupgrade/magic_resistance"), 2, true, 4);
        AddonDmnrArmorUpgradeInit.SMITE_DURATION = new MagicResistanceArmorUpgrade(RLoc.create("armorupgrade/smite_duration"), 1, true, 3);
    }
}
