package de.joh.fnc.common.spell.component;

import com.mna.api.affinity.Affinity;
import com.mna.api.faction.IFaction;
import com.mna.api.spells.ComponentApplicationResult;
import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.attributes.Attribute;
import com.mna.api.spells.attributes.AttributeValuePair;
import com.mna.api.spells.base.IModifiedSpellPart;
import com.mna.api.spells.parts.SpellEffect;
import com.mna.api.spells.targeting.SpellContext;
import com.mna.api.spells.targeting.SpellSource;
import com.mna.api.spells.targeting.SpellTarget;
import com.mna.spells.components.PotionEffectComponent;
import de.joh.fnc.common.capability.GamblerBlowCapabilityProvider;
import de.joh.fnc.common.init.EffectInit;
import de.joh.fnc.common.init.FactionInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class FinalDualComponent extends PotionEffectComponent {

    public FinalDualComponent(ResourceLocation icon) {
        super(icon, EffectInit.FINAL_DUAL, new AttributeValuePair(Attribute.DURATION, 15.0F, 15.0F, 300.0F, 15, 0.5F), new AttributeValuePair(Attribute.MAGNITUDE, 1.0F, 1.0F, 10.0F, 1, 3.0F), new AttributeValuePair(Attribute.PRECISION, 1.0F, 1.0F, 3.0F, 1, 10.0F));
    }

    @Override
    public ComponentApplicationResult ApplyEffect(SpellSource source, SpellTarget target, IModifiedSpellPart<SpellEffect> modificationData, SpellContext context) {
        if (target.isLivingEntity() && source.getCaster() instanceof Player caster) {
            int precision = (int)modificationData.getValue(Attribute.PRECISION);

            target.getLivingEntity().getCapability(GamblerBlowCapabilityProvider.GAMBLER_BLOW).ifPresent(gamblerBlowCapability -> {
                gamblerBlowCapability.setFinalDualEnemie(caster.getUUID());
                gamblerBlowCapability.setFinalDualPrecision(precision);
            });

            caster.getCapability(GamblerBlowCapabilityProvider.GAMBLER_BLOW).ifPresent(gamblerBlowCapability -> {
                gamblerBlowCapability.setFinalDualLost(false);
            });
        }

        return super.ApplyEffect(source, target, modificationData, context);
    }

    public int requiredXPForRote() {
        return 200;
    }

    public Affinity getAffinity() {
        return Affinity.BLOOD;
    }

    @Override
    public SpellPartTags getUseTag() {
        return SpellPartTags.HARMFUL;
    }

    public float initialComplexity() {
        return 10.0F;
    }

    @Override
    public IFaction getFactionRequirement() {
        return FactionInit.BLOOD_MAGE;
    }
}