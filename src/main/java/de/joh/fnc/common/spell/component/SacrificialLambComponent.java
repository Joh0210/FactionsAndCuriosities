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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

/**
 * A spell that marks a target. If the target takes damage, it is used as a sacrifice for the blood mage who casted the spell.
 * @author Joh0210
 */
public class SacrificialLambComponent extends PotionEffectComponent {
    public SacrificialLambComponent(ResourceLocation icon) {
        super(icon, EffectInit.SACRIFICIAL_LAMB, new AttributeValuePair(Attribute.DURATION, 30.0F, 5.0F, 600.0F, 5.0F, 1.0F));
    }

    @Override
    public ComponentApplicationResult ApplyEffect(SpellSource source, SpellTarget target, IModifiedSpellPart<SpellEffect> modificationData, SpellContext context) {
        if (target.isLivingEntity() && source.getCaster() instanceof Player caster) {
            if (caster == target.getLivingEntity()) {
                source.getPlayer().displayClientMessage(Component.translatable("fnc:components/alternativerecall.not_self"), true);
                return ComponentApplicationResult.FAIL;
            }

            target.getLivingEntity().getCapability(GamblerBlowCapabilityProvider.GAMBLER_BLOW).ifPresent(cap -> cap.setSacrificialLambSource(caster.getUUID()));
        }
        return super.ApplyEffect(source, target, modificationData, context);
    }

    @Override
    public float initialComplexity() {
        return 10.0F;
    }

    @Override
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

    @Override
    public IFaction getFactionRequirement() {
        return FactionInit.BLOOD_MAGE;
    }
}
