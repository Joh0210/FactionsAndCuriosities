package de.joh.fnc.common.init;

import com.mna.Registries;
import com.mna.api.affinity.Affinity;
import com.mna.api.spells.attributes.Attribute;
import com.mna.api.spells.attributes.AttributeValuePair;
import com.mna.api.spells.parts.Shape;
import com.mna.api.spells.parts.SpellEffect;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.api.spell.component.SmiteComponent;
import de.joh.fnc.common.spell.component.CauseWildMagicComponent;
import de.joh.fnc.common.spell.component.RandomTpComponent;
import de.joh.fnc.common.spell.component.WildDamageComponent;
import de.joh.fnc.common.spell.shape.PaladinSmiteShape;
import de.joh.fnc.common.util.RLoc;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

/**
 * Register all spell-components and shapes. Call via the event bus.
 * @author Joh0210
 */
@Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SpellInit {
    public static Shape SMITE;

    public static SpellEffect WILD_DAMAGE;
    public static SpellEffect CAUSE_WILD_MAGIC;
    public static SpellEffect RANDOM_TP;
    public static SpellEffect BURNING_SMITE;
    public static SpellEffect CRIPPLING_SMITE;
    public static SpellEffect DISPELLING_SMITE;
    public static SpellEffect SILENCING_SMITE;
    public static SpellEffect HEXING_SMITE;
    public static SpellEffect BOOMING_SMITE;

    @SubscribeEvent
    public static void register(RegisterEvent event) {
        event.register(Registries.Shape.get().getRegistryKey(), (helper) -> helper.register(RLoc.create("shapes/paladin_smite"), SMITE));
        event.register(Registries.SpellEffect.get().getRegistryKey(), (helper) -> {
            helper.register(RLoc.create("components/wild_damage"), WILD_DAMAGE);
            helper.register(RLoc.create("components/cause_wild_magic"), CAUSE_WILD_MAGIC);
            helper.register(RLoc.create("components/random_tp"), RANDOM_TP);
            helper.register(RLoc.create("components/burning_smite"), BURNING_SMITE);
            helper.register(RLoc.create("components/crippling_smite"), CRIPPLING_SMITE);
            helper.register(RLoc.create("components/dispelling_smite"), DISPELLING_SMITE);
            helper.register(RLoc.create("components/silencing_smite"), SILENCING_SMITE);
            helper.register(RLoc.create("components/hexing_smite"), HEXING_SMITE);
            helper.register(RLoc.create("components/booming_smite"), BOOMING_SMITE);
        });
    }

    static {
        SpellInit.SMITE = new PaladinSmiteShape(RLoc.create("textures/spell/shape/paladin_smite.png"));

        SpellInit.WILD_DAMAGE = new WildDamageComponent(RLoc.create("textures/spell/component/wild_damage.png"));
        SpellInit.CAUSE_WILD_MAGIC = new CauseWildMagicComponent(RLoc.create("textures/spell/component/cause_wild_magic.png"));
        SpellInit.RANDOM_TP = new RandomTpComponent(RLoc.create("textures/spell/component/random_tp.png"));
        SpellInit.BURNING_SMITE = new SmiteComponent(RLoc.create("textures/spell/component/burning_smite.png"),
                10, Affinity.FIRE, EffectInit.BURNING_SMITE,
                new AttributeValuePair(Attribute.DURATION, 3.0F, 0.0F, 10.0F, 1.0F, 2.0F));
        SpellInit.CRIPPLING_SMITE = new SmiteComponent(RLoc.create("textures/spell/component/crippling_smite.png"),
                15, Affinity.ENDER, EffectInit.CRIPPLING_SMITE,
                new AttributeValuePair(Attribute.DURATION, 5.0F, 5.0F, 20.0F, 5.0F, 3F),
                new AttributeValuePair(Attribute.MAGNITUDE, 1.0F, 1.0F, 2.0F, 1.0F, 10F));
        SpellInit.DISPELLING_SMITE = new SmiteComponent(RLoc.create("textures/spell/component/dispelling_smite.png"),
                15, Affinity.ARCANE, EffectInit.DISPELLING_SMITE,
                new AttributeValuePair(Attribute.MAGNITUDE, 1.0F, 1.0F, 3.0F, 1.0F, 10F));
        SpellInit.SILENCING_SMITE = new SmiteComponent(RLoc.create("textures/spell/component/silencing_smite.png"),
                20, Affinity.WIND, EffectInit.SILENCING_SMITE,
                new AttributeValuePair(Attribute.DURATION, 5.0F, 5.0F, 15.0F, 1.0F, 10.0F));
        SpellInit.HEXING_SMITE = new SmiteComponent(RLoc.create("textures/spell/component/hexing_smite.png"),
                10, Affinity.ENDER, EffectInit.HEXING_SMITE,
                new AttributeValuePair(Attribute.DURATION, 5.0F, 5.0F, 20.0F, 1.0F, 5.0F),
                new AttributeValuePair(Attribute.MAGNITUDE, 1.0F, 1.0F, 4.0F, 1.0F, 10F));
        SpellInit.BOOMING_SMITE = new SmiteComponent(RLoc.create("textures/spell/component/booming_smite.png"),
                20, Affinity.FIRE, EffectInit.BOOMING_SMITE,
                new AttributeValuePair(Attribute.MAGNITUDE, 5.0F, 5.0F, 15.0F, 1.0F, 6.0F),
                new AttributeValuePair(Attribute.PRECISION, 1.0F, 0.0F, 3.0F, 1.0F, 10.0F));
    }
}
