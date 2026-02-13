package de.joh.fnc.common.init;

import com.mna.Registries;
import com.mna.api.faction.BaseFaction;
import com.mna.api.faction.IFaction;
import com.mna.capabilities.playerdata.magic.resources.CastingResourceGuiRegistry;
import com.mna.capabilities.playerdata.magic.resources.CastingResourceRegistry;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.common.faction.PaladinFaction;
import de.joh.fnc.common.faction.ResourceIDs;
import de.joh.fnc.common.faction.WildFaction;
import de.joh.fnc.common.faction.castingresource.PaladinShieldCastingResource;
import de.joh.fnc.common.faction.castingresource.PaladinSwordCastingResource;
import de.joh.fnc.common.faction.castingresource.WildCastingResource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FactionInit {
    public static final BaseFaction WILD = new WildFaction();
    public static final IFaction PALADIN = new PaladinFaction();


    @SubscribeEvent
    public static void registerFactions(RegisterEvent event) {
        event.register(Registries.Factions.get().getRegistryKey(), (helper) -> {
            helper.register(ResourceIDs.FACTION_WILD_ID, WILD);
            helper.register(ResourceIDs.FACTION_PALADIN_ID, PALADIN);
        });
    }

    @SubscribeEvent
    public static void registerResources(FMLCommonSetupEvent event) {
        CastingResourceRegistry.Instance.register(ResourceIDs.PALADIN_MANA_SHIELD, PaladinShieldCastingResource.class);
        CastingResourceRegistry.Instance.register(ResourceIDs.PALADIN_MANA_SWORD, PaladinSwordCastingResource.class);
        CastingResourceRegistry.Instance.register(ResourceIDs.WILD_MANA, WildCastingResource.class);
    }


    @Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Client {
        @SubscribeEvent
        public static void registerResourceGuis(FMLClientSetupEvent event) {
            CastingResourceGuiRegistry.Instance.registerResourceGui(ResourceIDs.PALADIN_MANA_SHIELD, new PaladinShieldCastingResource.ResourceGui());
            CastingResourceGuiRegistry.Instance.registerResourceGui(ResourceIDs.PALADIN_MANA_SWORD, new PaladinSwordCastingResource.ResourceGui());
            CastingResourceGuiRegistry.Instance.registerResourceGui(ResourceIDs.WILD_MANA, new WildCastingResource.ResourceGui());
        }
    }
}
