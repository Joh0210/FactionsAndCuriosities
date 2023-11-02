package de.joh.fnc.spelladjustment;

import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.spelladjustment.init.*;
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
    public static SpellAdjustment EMPOWERED_I;
    public static SpellAdjustment EMPOWERED_II;
    public static SpellAdjustment EMPOWERED_III;
    public static SpellAdjustment SHARE;
    public static SpellAdjustment DOUBLE_TROUBLE;
    public static SpellAdjustment DELAY;
    public static SpellAdjustment MINIMIZE;
    public static SpellAdjustment MAXIMIZE;
    public static SpellAdjustment POWER;
    public static SpellAdjustment REVERSAL;
    public static SpellAdjustment PRECISE;

    @SubscribeEvent
    public static void registerArmorUpgrades(final RegistryEvent.Register<SpellAdjustment> event) {
        event.getRegistry().register(SpellAdjustmentInit.CANCEL);
        event.getRegistry().register(SpellAdjustmentInit.EMPOWERED_I);
        event.getRegistry().register(SpellAdjustmentInit.EMPOWERED_II);
        event.getRegistry().register(SpellAdjustmentInit.EMPOWERED_III);
        event.getRegistry().register(SpellAdjustmentInit.SHARE);
        event.getRegistry().register(SpellAdjustmentInit.DOUBLE_TROUBLE);
        event.getRegistry().register(SpellAdjustmentInit.DELAY);
        event.getRegistry().register(SpellAdjustmentInit.MINIMIZE);
        event.getRegistry().register(SpellAdjustmentInit.MAXIMIZE);
        event.getRegistry().register(SpellAdjustmentInit.POWER);
        event.getRegistry().register(SpellAdjustmentInit.REVERSAL);
        event.getRegistry().register(SpellAdjustmentInit.PRECISE);
    }

    //todo: revise frequency
    static {
        SpellAdjustmentInit.CANCEL = new SpellAdjustmentCancel(RLoc.create("spelladjustment/cancel"), 7);
        SpellAdjustmentInit.EMPOWERED_I = new SpellAdjustmentEmpowered(RLoc.create("spelladjustment/empowered_i"), 7, 1);
        SpellAdjustmentInit.EMPOWERED_II = new SpellAdjustmentEmpowered(RLoc.create("spelladjustment/empowered_ii"), 7, 2);
        SpellAdjustmentInit.EMPOWERED_III = new SpellAdjustmentEmpowered(RLoc.create("spelladjustment/empowered_iii"), 7, 3);
        SpellAdjustmentInit.SHARE = new SpellAdjustmentShare(RLoc.create("spelladjustment/share"), 7);
        SpellAdjustmentInit.DOUBLE_TROUBLE = new SpellAdjustmentDoubleTrouble(RLoc.create("spelladjustment/double_trouble"), 7);
        SpellAdjustmentInit.DELAY = new SpellAdjustmentDelay(RLoc.create("spelladjustment/delay"), 7);
        SpellAdjustmentInit.MINIMIZE = new SpellAdjustmentMinimize(RLoc.create("spelladjustment/minimize"), 7);
        SpellAdjustmentInit.MAXIMIZE = new SpellAdjustmentMaximize(RLoc.create("spelladjustment/maximize"), 7);
        SpellAdjustmentInit.POWER = new SpellAdjustmentPower(RLoc.create("spelladjustment/power"), 7);
        SpellAdjustmentInit.REVERSAL = new SpellAdjustmentReversal(RLoc.create("spelladjustment/reversal"), 7);
        SpellAdjustmentInit.PRECISE = new SpellAdjustmentPrecise(RLoc.create("spelladjustment/precise"), 7);
    }
}