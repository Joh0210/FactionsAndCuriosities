package de.joh.fnc.common.init;

import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.api.spelladjustment.SpellAdjustment;
import de.joh.fnc.common.spelladjustment.*;
import de.joh.fnc.common.util.RLoc;
import de.joh.fnc.common.util.Registries;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

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
    public static SpellAdjustment RENEWAL;

    @SubscribeEvent
    public static void register(RegisterEvent event) {
        event.register(Registries.SPELL_ADJUSTMENTS.get().getRegistryKey(), (helper) -> {
            helper.register(SpellAdjustmentInit.CANCEL.getRegistryName(), SpellAdjustmentInit.CANCEL);
            helper.register(SpellAdjustmentInit.EMPOWERED_I.getRegistryName(), SpellAdjustmentInit.EMPOWERED_I);
            helper.register(SpellAdjustmentInit.EMPOWERED_II.getRegistryName(), SpellAdjustmentInit.EMPOWERED_II);
            helper.register(SpellAdjustmentInit.EMPOWERED_III.getRegistryName(), SpellAdjustmentInit.EMPOWERED_III);
            helper.register(SpellAdjustmentInit.SHARE.getRegistryName(), SpellAdjustmentInit.SHARE);
            helper.register(SpellAdjustmentInit.DOUBLE_TROUBLE.getRegistryName(), SpellAdjustmentInit.DOUBLE_TROUBLE);
            helper.register(SpellAdjustmentInit.DELAY.getRegistryName(), SpellAdjustmentInit.DELAY);
            helper.register(SpellAdjustmentInit.MINIMIZE.getRegistryName(), SpellAdjustmentInit.MINIMIZE);
            helper.register(SpellAdjustmentInit.MAXIMIZE.getRegistryName(), SpellAdjustmentInit.MAXIMIZE);
            helper.register(SpellAdjustmentInit.POWER.getRegistryName(), SpellAdjustmentInit.POWER);
            helper.register(SpellAdjustmentInit.REVERSAL.getRegistryName(), SpellAdjustmentInit.REVERSAL);
            helper.register(SpellAdjustmentInit.PRECISE.getRegistryName(), SpellAdjustmentInit.PRECISE);
            helper.register(SpellAdjustmentInit.RENEWAL.getRegistryName(), SpellAdjustmentInit.RENEWAL);
        });
    }

    static {
        SpellAdjustmentInit.CANCEL = new CancelSpellAdjustment(RLoc.create("spelladjustment/cancel"), 3);
        SpellAdjustmentInit.EMPOWERED_I = new EmpoweredSpellAdjustment(RLoc.create("spelladjustment/empowered_i"), 7, 4);
        SpellAdjustmentInit.EMPOWERED_II = new EmpoweredSpellAdjustment(RLoc.create("spelladjustment/empowered_ii"), 7, 2);
        SpellAdjustmentInit.EMPOWERED_III = new EmpoweredSpellAdjustment(RLoc.create("spelladjustment/empowered_iii"), 7, 1);
        SpellAdjustmentInit.SHARE = new ShareSpellAdjustment(RLoc.create("spelladjustment/share"), 4);
        SpellAdjustmentInit.DOUBLE_TROUBLE = new DoubleTroubleSpellAdjustment(RLoc.create("spelladjustment/double_trouble"), 3);
        SpellAdjustmentInit.DELAY = new DelaySpellAdjustment(RLoc.create("spelladjustment/delay"), 4);
        SpellAdjustmentInit.MINIMIZE = new MinimizeSpellAdjustment(RLoc.create("spelladjustment/minimize"), 2);
        SpellAdjustmentInit.MAXIMIZE = new MaximizeSpellAdjustment(RLoc.create("spelladjustment/maximize"), 2);
        SpellAdjustmentInit.POWER = new PowerSpellAdjustment(RLoc.create("spelladjustment/power"), 5);
        SpellAdjustmentInit.REVERSAL = new ReversalSpellAdjustment(RLoc.create("spelladjustment/reversal"), 1);
        SpellAdjustmentInit.PRECISE = new PreciseSpellAdjustment(RLoc.create("spelladjustment/precise"), 5);
        SpellAdjustmentInit.RENEWAL = new RenewalSpellAdjustment(RLoc.create("spelladjustment/renewal"), 5);
    }
}