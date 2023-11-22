package de.joh.fnc.common.init;

import com.mna.api.rituals.RitualEffect;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.common.ritual.WildEnergyRitual;
import de.joh.fnc.common.util.RLoc;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Register all rituals.
 * @author Joh0210
 */
@Mod.EventBusSubscriber(modid= FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RitualInit {
    @SubscribeEvent
    public static void registerRitualEffects(RegistryEvent.Register<RitualEffect> event) {
        event.getRegistry().registerAll(
                new WildEnergyRitual(RLoc.create("rituals/wild_energy")).setRegistryName(RLoc.create("ritual-wild_energy"))
        );
    }
}
