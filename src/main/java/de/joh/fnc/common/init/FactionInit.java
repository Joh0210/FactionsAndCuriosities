package de.joh.fnc.common.init;

import com.mna.Registries;
import com.mna.api.faction.BaseFaction;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.common.faction.PaladinFaction;
import de.joh.fnc.common.faction.ResourceIDs;
import de.joh.fnc.common.faction.WildFaction;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FactionInit {
    public static final BaseFaction WILD = new WildFaction();
    public static final BaseFaction PALADIN = new PaladinFaction();


    @SubscribeEvent
    public static void registerFactions(RegisterEvent event) {
        event.register(Registries.Factions.get().getRegistryKey(), (helper) -> {
            helper.register(ResourceIDs.FACTION_WILD_ID, WILD);
            helper.register(ResourceIDs.FACTION_PALADIN_ID, PALADIN);
        });
    }
}
