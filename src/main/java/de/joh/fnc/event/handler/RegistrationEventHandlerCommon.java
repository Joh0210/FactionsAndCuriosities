package de.joh.fnc.event.handler;

import com.mna.api.events.CastingResourceRegistrationEvent;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.factions.castingresource.CastingResourceIDs;
import de.joh.fnc.factions.castingresource.WildMana;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RegistrationEventHandlerCommon {
    @SubscribeEvent
    public static void onCastingResourceRegistrationEvent(CastingResourceRegistrationEvent event){
        event.getRegistry().register(CastingResourceIDs.WILD_MANA, WildMana.class);
    }
}
