package de.joh.fnc.common.init;

import com.mna.api.affinity.Affinity;
import com.mna.api.spells.attributes.Attribute;
import com.mna.api.spells.attributes.AttributeValuePair;
import com.mna.api.spells.parts.SpellEffect;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.common.spell.component.CauseWildMagicComponent;
import de.joh.fnc.common.spell.component.RandomTpComponent;
import de.joh.fnc.api.spell.component.SmiteComponent;
import de.joh.fnc.common.spell.component.WildDamageComponent;
import de.joh.fnc.common.util.RLoc;
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
    public static SpellEffect BURNING_SMITE;

    @SubscribeEvent
    public static void registerComponents(final RegistryEvent.Register<SpellEffect> event) {
        event.getRegistry().register(SpellInit.WILD_DAMAGE);
        event.getRegistry().register(SpellInit.CAUSE_WILD_MAGIC);
        event.getRegistry().register(SpellInit.RANDOM_TP);
        event.getRegistry().register(SpellInit.BURNING_SMITE);
    }

    static {
        SpellInit.WILD_DAMAGE = new WildDamageComponent(RLoc.create("components/wild_damage"), RLoc.create("textures/spell/component/wild_damage.png"));
        SpellInit.CAUSE_WILD_MAGIC = new CauseWildMagicComponent(RLoc.create("components/cause_wild_magic"), RLoc.create("textures/spell/component/cause_wild_magic.png"));
        SpellInit.RANDOM_TP = new RandomTpComponent(RLoc.create("components/random_tp"), RLoc.create("textures/spell/component/random_tp.png"));
        SpellInit.BURNING_SMITE = new SmiteComponent(RLoc.create("components/burning_smite"), RLoc.create("textures/spell/component/burning_smite.png"), 10, Affinity.FIRE, EffectInit.BURNING_SMITE, new AttributeValuePair(Attribute.DAMAGE, 1.0F, 1.0F, 5.0F, 1F, 20F), new AttributeValuePair(Attribute.DURATION, 3.0F, 0.0F, 10.0F, 1.0F, 2.0F));
    }
}
