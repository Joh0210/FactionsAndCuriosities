package de.joh.fnc.wildmagic;

import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.utils.RLoc;
import de.joh.fnc.wildmagic.init.WildMagicRandomTp;
import de.joh.fnc.wildmagic.util.Quality;
import de.joh.fnc.wildmagic.util.WildMagic;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * An initialization of all Wild Magics.
 * @see WildMagic
 * @author Joh0210
 */
@Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WildMagicInit {
    public static WildMagic RANDOM_SELF_TP;
    public static WildMagic RANDOM_OTHER_TP;

    @SubscribeEvent
    public static void registerArmorUpgrades(final RegistryEvent.Register<WildMagic> event) {
        event.getRegistry().register(WildMagicInit.RANDOM_SELF_TP);
        event.getRegistry().register(WildMagicInit.RANDOM_OTHER_TP);
    }

    //todo: revise upgrade costs and recipes
    static {
        WildMagicInit.RANDOM_SELF_TP = new WildMagicRandomTp(RLoc.create("wildmagic/random_self_tp"), 7, Quality.BAD, 32, true);
        WildMagicInit.RANDOM_OTHER_TP = new WildMagicRandomTp(RLoc.create("wildmagic/random_other_tp"), 7, Quality.BAD, 32, false);
    }
}
