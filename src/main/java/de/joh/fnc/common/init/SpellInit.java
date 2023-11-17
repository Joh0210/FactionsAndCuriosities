package de.joh.fnc.common.init;

import com.mna.api.spells.parts.SpellEffect;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.common.spell.component.CauseWildMagicComponent;
import de.joh.fnc.common.spell.component.RandomTpComponent;
import de.joh.fnc.common.spell.component.WildDamageComponent;
import de.joh.fnc.common.utils.RLoc;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Register all spell-components and shapes. Call via the event bus.
 * @author Joh0210
 */
@Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SpellInit {
    public static SpellEffect WILD_DAMAGE;
    public static SpellEffect CAUSE_WILD_MAGIC;
    public static SpellEffect RANDOM_TP;

    @SubscribeEvent
    public static void registerComponents(final RegistryEvent.Register<SpellEffect> event) {
        event.getRegistry().register(SpellInit.WILD_DAMAGE);
        event.getRegistry().register(SpellInit.CAUSE_WILD_MAGIC);
        event.getRegistry().register(SpellInit.RANDOM_TP);
    }

    static {
        SpellInit.WILD_DAMAGE = new WildDamageComponent(RLoc.create("components/wild_damage"), RLoc.create("textures/spell/component/wild_damage.png"));
        SpellInit.CAUSE_WILD_MAGIC = new CauseWildMagicComponent(RLoc.create("components/cause_wild_magic"), RLoc.create("textures/spell/component/cause_wild_magic.png"));
        SpellInit.RANDOM_TP = new RandomTpComponent(RLoc.create("components/random_tp"), RLoc.create("textures/spell/component/random_tp.png"));
    }
}
