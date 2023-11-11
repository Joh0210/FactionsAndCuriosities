package de.joh.fnc.compat.dmnr.armorupgrades;

import de.joh.dragonmagicandrelics.armorupgrades.types.ArmorUpgrade;
import de.joh.dragonmagicandrelics.item.items.dragonmagearmor.DragonMageArmor;
import de.joh.fnc.utils.RLoc;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * An initialization of the F&C upgrades for the Dragon Mage Armor.
 * @see DragonMageArmor
 * @author Joh0210
 */
public class AddonDragonMagicAndRelicsArmorUpgradeInit {
    public static ArmorUpgrade WILD_MAGIC_LUCK;
    public static ArmorUpgrade RANDOM_SPELL_ADJUSTMENT; //RandomSpellAdjustment

    @SubscribeEvent
    public static void registerArmorUpgrades(final RegistryEvent.Register<ArmorUpgrade> event) {
        event.getRegistry().register(AddonDragonMagicAndRelicsArmorUpgradeInit.WILD_MAGIC_LUCK);
        event.getRegistry().register(AddonDragonMagicAndRelicsArmorUpgradeInit.RANDOM_SPELL_ADJUSTMENT);
    }

    static {
        AddonDragonMagicAndRelicsArmorUpgradeInit.WILD_MAGIC_LUCK = new ArmorUpgradeWildMagicLuck(RLoc.create("armorupgrade/wild_magic_luck"), 7);
        AddonDragonMagicAndRelicsArmorUpgradeInit.RANDOM_SPELL_ADJUSTMENT = new ArmorUpgrade(RLoc.create("armorupgrade/random_spell_adjustment"), 2, true, 4);
    }
}
