package de.joh.fnc.event.handler;

import com.mna.api.events.CastingResourceGuiRegistrationEvent;
import com.mna.api.events.CastingResourceRegistrationEvent;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.factions.castingresource.ResourceIDs;
import de.joh.fnc.factions.castingresource.WildMana;
import de.joh.fnc.factions.castingresource.WildManaGui;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class RegistrationEventHandler{
    @Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class RegistrationEventHandlerClient {
        @SubscribeEvent
        public static void onCastingResourceRegistrationEvent(CastingResourceGuiRegistrationEvent event){
            event.getRegistry().registerResourceGui(ResourceIDs.WILD_MANA, new WildManaGui());
        }
    }

    @Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class RegistrationEventHandlerCommon {
        @SubscribeEvent
        public static void onCastingResourceRegistrationEvent(CastingResourceRegistrationEvent event){
            event.getRegistry().register(ResourceIDs.WILD_MANA, WildMana.class);
        }
    }
}