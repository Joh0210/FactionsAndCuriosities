package de.joh.fnc.spelladjustment;

import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.spelladjustment.init.SpellAdjustmentCancel;
import de.joh.fnc.spelladjustment.util.SpellAdjustment;
import de.joh.fnc.utils.RLoc;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * An initialization of all Wild Magics.
 * @see SpellAdjustment
 * @author Joh0210
 */
@Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SpellAdjustmentInit {
    public static SpellAdjustment CANCEL;

    @SubscribeEvent
    public static void registerArmorUpgrades(final RegistryEvent.Register<SpellAdjustment> event) {
        event.getRegistry().register(SpellAdjustmentInit.CANCEL);
    }

    //todo: revise frequency
    static {
        SpellAdjustmentInit.CANCEL = new SpellAdjustmentCancel(RLoc.create("spelladjustment/cancel"), 7);
    }
}