package de.joh.fnc.spell;

import com.mna.api.spells.parts.SpellEffect;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.spell.component.CauseWildMagic;
import de.joh.fnc.spell.component.WildDamage;
import de.joh.fnc.utils.RLoc;
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

    @SubscribeEvent
    public static void registerComponents(final RegistryEvent.Register<SpellEffect> event) {
        event.getRegistry().register(SpellInit.WILD_DAMAGE);
        event.getRegistry().register(SpellInit.CAUSE_WILD_MAGIC);
    }

    static {
        SpellInit.WILD_DAMAGE = new WildDamage(RLoc.create("components/wild_damage"), RLoc.create("textures/spell/component/wild_damage.png"));
        SpellInit.CAUSE_WILD_MAGIC = new CauseWildMagic(RLoc.create("components/cause_wild_magic"), RLoc.create("textures/spell/component/cause_wild_magic.png"));
    }
}
