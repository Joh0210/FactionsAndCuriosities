package de.joh.fnc.common.init;

import com.mna.api.faction.BaseFaction;
import com.mna.api.faction.IFaction;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.common.faction.WildFaction;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FactionInit {
    public static final BaseFaction WILD = new WildFaction();

    @SubscribeEvent
    public static void registerFactions(RegistryEvent.Register<IFaction> event) {
        event.getRegistry().register(WILD);
    }
}
