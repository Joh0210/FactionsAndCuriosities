package de.joh.fnc.event.handler;

import com.mna.api.events.CastingResourceGuiRegistrationEvent;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.factions.castingresource.CastingResourceIDs;
import de.joh.fnc.factions.castingresource.WildManaGui;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class RegistrationEventHandlerClient {
    @SubscribeEvent
    public static void onCastingResourceRegistrationEvent(CastingResourceGuiRegistrationEvent event){
        event.getRegistry().registerResourceGui(CastingResourceIDs.WILD_MANA, new WildManaGui());
    }
}