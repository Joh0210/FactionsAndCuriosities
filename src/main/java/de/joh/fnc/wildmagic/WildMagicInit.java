package de.joh.fnc.wildmagic;

import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.utils.RLoc;
import de.joh.fnc.wildmagic.init.*;
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
    public static WildMagic RANDOM_TP_SELF;
    public static WildMagic RANDOM_TP_TARGET;
    public static WildMagic REPAIR_SELF;
    public static WildMagic EXPLODE_WEAK_SELF;
    //public static WildMagic EXPLODE_FAKE_SELF;
    public static WildMagic EXPLODE_WEAK_TARGET;
    public static WildMagic EXPLODE_STRONG_SELF;
    public static WildMagic EXPLODE_STRONG_TARGET;
    public static WildMagic EXPLODE_SPARE_SELF;
    public static WildMagic EXPLODE_SPARE_TARGET;
    public static WildMagic NAUSEA_SELF;
    public static WildMagic NAUSEA_TARGET;
    public static WildMagic CHRONO_ANCHOR_SELF;
    public static WildMagic POISON_WEAK_SELF;
    public static WildMagic POISON_WEAK_TARGET;
    public static WildMagic POISON_STRONG_SELF;
    public static WildMagic POISON_STRONG_TARGET;
    public static WildMagic WITHER_WEAK_SELF;
    public static WildMagic WITHER_WEAK_TARGET;
    public static WildMagic WITHER_STRONG_SELF;
    public static WildMagic WITHER_STRONG_TARGET;
    public static WildMagic LIGHTNING_SELF;
    public static WildMagic LIGHTNING_TARGET;
    public static WildMagic LIVING_BOMB_SELF;
    public static WildMagic LIVING_BOMB_TARGET;
    public static WildMagic MAXIMIZED_SELF;
    public static WildMagic MAXIMIZED_TARGET;
    public static WildMagic MINIMIZED_SELF;
    public static WildMagic MINIMIZED_TARGET;
    public static WildMagic EMPOWERED_I_SELF;
    public static WildMagic EMPOWERED_I_TARGET;
    public static WildMagic EMPOWERED_II_SELF;
    public static WildMagic EMPOWERED_II_TARGET;
    public static WildMagic EMPOWERED_III_SELF;
    public static WildMagic EMPOWERED_III_TARGET;
    public static WildMagic RANDOM_ORE_MINER;
    public static WildMagic SPELL_ADJUSTMENT;

    @SubscribeEvent
    public static void registerArmorUpgrades(final RegistryEvent.Register<WildMagic> event) {
        event.getRegistry().register(WildMagicInit.RANDOM_TP_SELF);
        event.getRegistry().register(WildMagicInit.RANDOM_TP_TARGET);
        event.getRegistry().register(WildMagicInit.REPAIR_SELF);
        //event.getRegistry().register(WildMagicInit.EXPLODE_FAKE_SELF);
        event.getRegistry().register(WildMagicInit.EXPLODE_WEAK_SELF);
        event.getRegistry().register(WildMagicInit.EXPLODE_WEAK_TARGET);
        event.getRegistry().register(WildMagicInit.EXPLODE_STRONG_SELF);
        event.getRegistry().register(WildMagicInit.EXPLODE_STRONG_TARGET);
        event.getRegistry().register(WildMagicInit.EXPLODE_SPARE_SELF);
        event.getRegistry().register(WildMagicInit.EXPLODE_SPARE_TARGET);
        event.getRegistry().register(WildMagicInit.NAUSEA_SELF);
        event.getRegistry().register(WildMagicInit.NAUSEA_TARGET);
        event.getRegistry().register(WildMagicInit.CHRONO_ANCHOR_SELF);
        event.getRegistry().register(WildMagicInit.POISON_WEAK_SELF);
        event.getRegistry().register(WildMagicInit.POISON_WEAK_TARGET);
        event.getRegistry().register(WildMagicInit.POISON_STRONG_SELF);
        event.getRegistry().register(WildMagicInit.POISON_STRONG_TARGET);
        event.getRegistry().register(WildMagicInit.WITHER_WEAK_SELF);
        event.getRegistry().register(WildMagicInit.WITHER_WEAK_TARGET);
        event.getRegistry().register(WildMagicInit.WITHER_STRONG_SELF);
        event.getRegistry().register(WildMagicInit.WITHER_STRONG_TARGET);
        event.getRegistry().register(WildMagicInit.LIGHTNING_SELF);
        event.getRegistry().register(WildMagicInit.LIGHTNING_TARGET);
        event.getRegistry().register(WildMagicInit.LIVING_BOMB_SELF);
        event.getRegistry().register(WildMagicInit.LIVING_BOMB_TARGET);
        event.getRegistry().register(WildMagicInit.MAXIMIZED_SELF);
        event.getRegistry().register(WildMagicInit.MAXIMIZED_TARGET);
        event.getRegistry().register(WildMagicInit.MINIMIZED_SELF);
        event.getRegistry().register(WildMagicInit.MINIMIZED_TARGET);
        event.getRegistry().register(WildMagicInit.EMPOWERED_I_SELF);
        event.getRegistry().register(WildMagicInit.EMPOWERED_I_TARGET);
        event.getRegistry().register(WildMagicInit.EMPOWERED_II_SELF);
        event.getRegistry().register(WildMagicInit.EMPOWERED_II_TARGET);
        event.getRegistry().register(WildMagicInit.EMPOWERED_III_SELF);
        event.getRegistry().register(WildMagicInit.EMPOWERED_III_TARGET);
        event.getRegistry().register(WildMagicInit.RANDOM_ORE_MINER);
        event.getRegistry().register(WildMagicInit.SPELL_ADJUSTMENT);
    }

    //todo: revise frequency
    static {
        WildMagicInit.RANDOM_TP_SELF = new WildMagicRandomTp(RLoc.create("wildmagic/random_tp_self"), 7, 32, true);
        WildMagicInit.RANDOM_TP_TARGET = new WildMagicRandomTp(RLoc.create("wildmagic/random_tp_target"), 7, 32, false);
        WildMagicInit.REPAIR_SELF = new WildMagicRepair(RLoc.create("wildmagic/repair_self"), 7);
        //WildMagicInit.EXPLODE_FAKE_SELF = new WildMagicExplosion(RLoc.create("wildmagic/explode_fake_self"), 7, true, 0, false);
        WildMagicInit.EXPLODE_WEAK_SELF = new WildMagicExplosion(RLoc.create("wildmagic/explode_weak_self"), 7, true, 2, false);
        WildMagicInit.EXPLODE_WEAK_TARGET = new WildMagicExplosion(RLoc.create("wildmagic/explode_weak_target"), 7, false, 2, false);
        WildMagicInit.EXPLODE_STRONG_SELF = new WildMagicExplosion(RLoc.create("wildmagic/explode_strong_self"), 7, true, 4, false);
        WildMagicInit.EXPLODE_STRONG_TARGET = new WildMagicExplosion(RLoc.create("wildmagic/explode_strong_target"), 7, false, 4, false);
        WildMagicInit.EXPLODE_SPARE_SELF = new WildMagicExplosion(RLoc.create("wildmagic/explode_spare_self"), 7, true, 8, false, true);
        WildMagicInit.EXPLODE_SPARE_TARGET = new WildMagicExplosion(RLoc.create("wildmagic/explode_spare_target"), 7, false, 8, false, true);
        WildMagicInit.NAUSEA_SELF = new WildMagicNausea(RLoc.create("wildmagic/nausea_self"), 7, true);
        WildMagicInit.NAUSEA_TARGET = new WildMagicNausea(RLoc.create("wildmagic/nausea_target"), 7, false);
        WildMagicInit.CHRONO_ANCHOR_SELF = new WildMagicChronoAnchor(RLoc.create("wildmagic/chrono_anchor_self"), 7);
        WildMagicInit.POISON_STRONG_SELF = new WildMagicPoison(RLoc.create("wildmagic/poison_strong_self"), 7, true, 2);
        WildMagicInit.POISON_STRONG_TARGET = new WildMagicPoison(RLoc.create("wildmagic/poison_strong_target"), 7, false, 2);
        WildMagicInit.POISON_WEAK_SELF = new WildMagicPoison(RLoc.create("wildmagic/poison_weak_self"), 7, true, 1);
        WildMagicInit.POISON_WEAK_TARGET = new WildMagicPoison(RLoc.create("wildmagic/poison_weak_target"), 7, false, 1);
        WildMagicInit.WITHER_STRONG_SELF = new WildMagicWither(RLoc.create("wildmagic/wither_strong_self"), 7, true, 2);
        WildMagicInit.WITHER_STRONG_TARGET = new WildMagicWither(RLoc.create("wildmagic/wither_strong_target"), 7, false, 2);
        WildMagicInit.WITHER_WEAK_SELF = new WildMagicWither(RLoc.create("wildmagic/wither_weak_self"), 7, true, 1);
        WildMagicInit.WITHER_WEAK_TARGET = new WildMagicWither(RLoc.create("wildmagic/wither_weak_target"), 7, false, 1);
        WildMagicInit.LIGHTNING_SELF = new WildMagicLightning(RLoc.create("wildmagic/lightning_self"), 7, true);
        WildMagicInit.LIGHTNING_TARGET = new WildMagicLightning(RLoc.create("wildmagic/lightning_target"), 7, false);
        WildMagicInit.LIVING_BOMB_SELF = new WildMagicLivingBomb(RLoc.create("wildmagic/living_bomb_self"), 7, true, 1);
        WildMagicInit.LIVING_BOMB_TARGET = new WildMagicLivingBomb(RLoc.create("wildmagic/living_bomb_target"), 7, false, 1);
        WildMagicInit.MAXIMIZED_SELF = new WildMagicMaximize(RLoc.create("wildmagic/maximize_self"), 7, true);
        WildMagicInit.MAXIMIZED_TARGET = new WildMagicMaximize(RLoc.create("wildmagic/maximize_target"), 7, false);
        WildMagicInit.MINIMIZED_SELF = new WildMagicMinimize(RLoc.create("wildmagic/minimize_self"), 7, true);
        WildMagicInit.MINIMIZED_TARGET = new WildMagicMinimize(RLoc.create("wildmagic/minimize_target"), 7, false);
        WildMagicInit.EMPOWERED_I_SELF = new WildMagicEmpowered(RLoc.create("wildmagic/minimize_i_self"), 7, true, 1);
        WildMagicInit.EMPOWERED_I_TARGET = new WildMagicEmpowered(RLoc.create("wildmagic/minimize_i_target"), 7, false, 1);
        WildMagicInit.EMPOWERED_II_SELF = new WildMagicEmpowered(RLoc.create("wildmagic/minimize_ii_self"), 7, true, 2);
        WildMagicInit.EMPOWERED_II_TARGET = new WildMagicEmpowered(RLoc.create("wildmagic/minimize_ii_target"), 7, false, 2);
        WildMagicInit.EMPOWERED_III_SELF = new WildMagicEmpowered(RLoc.create("wildmagic/minimize_iii_self"), 7, true, 3);
        WildMagicInit.EMPOWERED_III_TARGET = new WildMagicEmpowered(RLoc.create("wildmagic/minimize_iii_target"), 7, false, 3);
        WildMagicInit.RANDOM_ORE_MINER = new WildMagicRandomOreMiner(RLoc.create("wildmagic/random_ore_miner_self"), 7);
        WildMagicInit.SPELL_ADJUSTMENT = new WildMagicRandomSpellAdjustment(RLoc.create("wildmagic/random_spell_adjustment_self"), 7);
    }
}
