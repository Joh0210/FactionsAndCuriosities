package de.joh.fnc.common.spell.component;

import com.mna.api.affinity.Affinity;
import com.mna.api.faction.IFaction;
import com.mna.api.sound.SFX;
import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.attributes.Attribute;
import com.mna.api.spells.attributes.AttributeValuePair;
import com.mna.spells.components.PotionEffectComponent;
import de.joh.fnc.common.init.EffectInit;
import de.joh.fnc.common.init.FactionInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

/**
 * A spell component that applies the Hex effect to the target.
 * @author Joh0210
 */
public class HexComponent extends PotionEffectComponent {

    public HexComponent(ResourceLocation icon) {
        super(icon, EffectInit.HEX,
                new AttributeValuePair(Attribute.DURATION, 30.0F, 5.0F, 300.0F, 5.0F, 5.0F),
                new AttributeValuePair(Attribute.MAGNITUDE, 1.0F, 1.0F, 4.0F, 1.0F, 15.0F)
        );
    }

    @Override
    public Affinity getAffinity() {
        return Affinity.BLOOD;
    }

    @Override
    public float initialComplexity() {
        return 20.0F;
    }

    @Override
    public int requiredXPForRote() {
        return 500;
    }

    @Override
    public SpellPartTags getUseTag() {
        return SpellPartTags.HARMFUL;
    }

    @Override
    public IFaction getFactionRequirement() {
        return FactionInit.BLOOD_MAGE;
    }

    @Override
    public SoundEvent SoundEffect() {
        return SFX.Spell.Buff.BLOOD;
    }
}
