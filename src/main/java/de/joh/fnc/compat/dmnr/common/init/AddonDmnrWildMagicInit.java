package de.joh.fnc.compat.dmnr.common.init;

import de.joh.dragonmagicandrelics.effects.EffectInit;
import de.joh.fnc.api.wildmagic.WildMagic;
import de.joh.fnc.api.wildmagic.WildMagicOtherPotionEffect;
import de.joh.fnc.common.util.RLoc;
import de.joh.fnc.compat.dmnr.common.wildmagic.PeaceWildMagic;
import de.joh.fnc.compat.dmnr.common.wildmagic.UltimateArmorWildMagic;
import de.joh.fnc.compat.dmnr.common.wildmagic.WarWildMagic;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * An initialization of all Wild Magics.
 * @see WildMagic
 * @author Joh0210
 */
public class AddonDmnrWildMagicInit {
    public static WildMagic FLY_III_SELF;
    public static WildMagic PEACE_SELF;
    public static WildMagic ULTIMATE_ARMOR_SELF;
    public static WildMagic WAR_SELF;

    @SubscribeEvent
    public static void registerArmorUpgrades(final RegistryEvent.Register<WildMagic> event) {
        event.getRegistry().register(AddonDmnrWildMagicInit.FLY_III_SELF);
        event.getRegistry().register(AddonDmnrWildMagicInit.PEACE_SELF);
        event.getRegistry().register(AddonDmnrWildMagicInit.ULTIMATE_ARMOR_SELF);
        event.getRegistry().register(AddonDmnrWildMagicInit.WAR_SELF);
    }

    static {
        AddonDmnrWildMagicInit.FLY_III_SELF = new WildMagicOtherPotionEffect(RLoc.create("wildmagic/fly_iii_self"), 3, true, EffectInit.ELYTRA, 6000, 3, true, true);
        AddonDmnrWildMagicInit.PEACE_SELF = new PeaceWildMagic(RLoc.create("wildmagic/peace_self"), 4, 12000);
        AddonDmnrWildMagicInit.WAR_SELF = new WarWildMagic(RLoc.create("wildmagic/war_self"), 4, 12000);
        AddonDmnrWildMagicInit.ULTIMATE_ARMOR_SELF = new UltimateArmorWildMagic(RLoc.create("wildmagic/ultimate_armor_self"), 2, 6000);

    }
}

