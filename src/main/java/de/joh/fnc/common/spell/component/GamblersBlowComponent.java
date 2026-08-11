package de.joh.fnc.common.spell.component;

import com.mna.api.affinity.Affinity;
import com.mna.api.faction.IFaction;
import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.attributes.Attribute;
import com.mna.api.spells.attributes.AttributeValuePair;
import com.mna.spells.components.PotionEffectComponent;
import de.joh.fnc.common.init.EffectInit;
import de.joh.fnc.common.init.FactionInit;
import net.minecraft.resources.ResourceLocation;

public class GamblersBlowComponent extends PotionEffectComponent {
    private final boolean isDefense;

    public GamblersBlowComponent(ResourceLocation icon, boolean isDefense) {
        super(icon, isDefense ? EffectInit.GAMBLERS_BLOW_DEFENSIVE : EffectInit.GAMBLERS_BLOW_OFFENSIVE, new AttributeValuePair(Attribute.DURATION, 15.0F, 5.0F, 90.0F, 15, 4.0F), new AttributeValuePair(Attribute.MAGNITUDE, 1.0F, 1.0F, 5.0F, 1, 6.0F));
        this.isDefense = isDefense;
    }

    public int requiredXPForRote() {
        return 200;
    }

    public Affinity getAffinity() {
        return Affinity.ARCANE;
    }

    @Override
    public SpellPartTags getUseTag() {
        return SpellPartTags.UTILITY;
    }

    public float initialComplexity() {
        return 10.0F;
    }

    @Override
    public IFaction getFactionRequirement() {
        return FactionInit.WILD;
    }
}

