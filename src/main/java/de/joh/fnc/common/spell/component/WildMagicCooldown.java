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

/**
 * Adds the WildMagicCooldown effect to the user to suppress Wild Magic for a longer period of time
 * @see de.joh.fnc.api.event.ShouldCauseWildMagicEvent
 * @author Joh0210
 */
public class WildMagicCooldown extends PotionEffectComponent {
    public WildMagicCooldown(ResourceLocation icon) {
        super(icon, EffectInit.WILD_MAGIC_COOLDOWN, new AttributeValuePair(Attribute.DURATION, 30.0F, 15.0F, 600.0F, 30.0F, 5.0F));
    }

    public int requiredXPForRote() {
        return 200;
    }

    public Affinity getAffinity() {
        return Affinity.ARCANE;
    }

    @Override
    public SpellPartTags getUseTag() {
        return SpellPartTags.FRIENDLY;
    }

    public float initialComplexity() {
        return 10.0F;
    }

    @Override
    public IFaction getFactionRequirement() {
        return FactionInit.WILD;
    }
}
