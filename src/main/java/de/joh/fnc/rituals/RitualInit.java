package de.joh.fnc.rituals;

import com.mna.api.rituals.RitualEffect;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.rituals.effects.WildEnergy;
import de.joh.fnc.utils.RLoc;
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
                new WildEnergy(RLoc.create("rituals/wild_energy")).setRegistryName(RLoc.create("ritual-wild_energy"))
        );
    }
}
