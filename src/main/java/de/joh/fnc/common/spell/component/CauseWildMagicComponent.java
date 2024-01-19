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
import de.joh.fnc.common.init.FactionInit;
import de.joh.fnc.api.util.Quality;
import de.joh.fnc.api.wildmagic.WildMagicHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

/**
 * This Damage Spell causes a random Wild Magic Effect.
 * <br> Precision Attribute:
 * <br> greater than one: Very Bad - Neutral Effects
 * <br> one: all Effects possible
 * <br> smaller than one: Neutral - Very Good Effects
 * @see WildMagicHelper
 * @author Joh0210
 */
public class CauseWildMagicComponent extends SpellEffect {
    public CauseWildMagicComponent(ResourceLocation icon) {
        super(icon, new AttributeValuePair(Attribute.PRECISION, 1.0F, 0.0F, 2.0F, 1.0F, 25.0F));
    }

    @Override
    public ComponentApplicationResult ApplyEffect(SpellSource spellSource, SpellTarget spellTarget, IModifiedSpellPart<SpellEffect> modificationData, SpellContext spellContext) {
        if(spellContext.getLevel().isClientSide){
            return  ComponentApplicationResult.SUCCESS;
        }

        LivingEntity target = spellTarget.getLivingEntity();

        if(target == null){
            return ComponentApplicationResult.FAIL;
        }

        WildMagicHelper.WildMagicFilters filter;
        if(Math.round(modificationData.getValue(Attribute.PRECISION)) > 1.0f){
            filter = (wm, s, t, ct) -> wm.getQuality(SpellPartTags.FRIENDLY).ordinal() <= Quality.NEUTRAL.ordinal();
        }
        else if(Math.round(modificationData.getValue(Attribute.PRECISION)) < 1.0f){
            filter = (wm, s, t, ct) -> wm.getQuality(SpellPartTags.FRIENDLY).ordinal() >= Quality.NEUTRAL.ordinal();
        } else {
            filter = (wm, s, t, ct) -> true;
        }

        return WildMagicHelper.performRandomWildMagic(target, spellTarget, SpellPartTags.FRIENDLY, filter) ? ComponentApplicationResult.SUCCESS : ComponentApplicationResult.FAIL;
    }

    @Override
    public SpellPartTags getUseTag() {
        return SpellPartTags.HARMFUL;
    }

    @Override
    public Affinity getAffinity() {
        return Affinity.ARCANE;
    }

    @Override
    public IFaction getFactionRequirement() {
        return FactionInit.WILD;
    }

    @Override
    public float initialComplexity() {
        return 15.0F;
    }

    @Override
    public int requiredXPForRote() {
        return 200;
    }

    @Override
    public boolean targetsBlocks() {
        return false;
    }

    @Override
    public boolean targetsEntities() {
        return true;
    }

    @Override
    public boolean canBeChanneled() {
        return false;
    }
}


