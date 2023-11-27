package de.joh.fnc.common.event;

import com.mna.api.events.CastingResourceGuiRegistrationEvent;
import com.mna.api.events.CastingResourceRegistrationEvent;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.common.faction.ResourceIDs;
import de.joh.fnc.common.faction.castingresource.PaladinCastingResource;
import de.joh.fnc.common.faction.castingresource.PaladinCastingResourceGui;
import de.joh.fnc.common.faction.castingresource.WildCastingResource;
import de.joh.fnc.common.faction.castingresource.WildCastingResourceGui;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class RegistrationEventHandler{
    @Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class RegistrationEventHandlerClient {
        @SubscribeEvent
        public static void onCastingResourceRegistrationEvent(CastingResourceGuiRegistrationEvent event){
            event.getRegistry().registerResourceGui(ResourceIDs.WILD_MANA, new WildCastingResourceGui());
            event.getRegistry().registerResourceGui(ResourceIDs.PALADIN_MANA, new PaladinCastingResourceGui());
        }
    }

    @Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class RegistrationEventHandlerCommon {
        @SubscribeEvent
        public static void onCastingResourceRegistrationEvent(CastingResourceRegistrationEvent event){
            event.getRegistry().register(ResourceIDs.WILD_MANA, WildCastingResource.class);
            event.getRegistry().register(ResourceIDs.PALADIN_MANA, PaladinCastingResource.class);
        }
    }
}