package de.joh.fnc.common.init;

import com.mna.Registries;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.common.ritual.PactRitual;
import de.joh.fnc.common.ritual.WildEnergyRitual;
import de.joh.fnc.common.util.RLoc;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

/**
 * Register all rituals.
 * @author Joh0210
 */
@Mod.EventBusSubscriber(modid= FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RitualInit {

    @SubscribeEvent
    public static void registerRitualEffects(RegisterEvent event) {
        event.register(Registries.RitualEffect.get().getRegistryKey(), (helper) -> {
            helper.register(RLoc.create("ritual-wild_energy"), new WildEnergyRitual(RLoc.create("rituals/wild_energy")));
            helper.register(RLoc.create("ritual-pact"), new PactRitual(RLoc.create("rituals/pact")));
        });
    }
}
