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
import com.mna.tools.TeleportHelper;
import de.joh.fnc.common.init.FactionInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

/**
 * Randomly Teleports the Target
 * @author Joh0210
 */
public class RandomTpComponent extends SpellEffect {
    /**
     * How often should the TP be tried.
     */
    public static final int TRIES = 10;

    public RandomTpComponent(ResourceLocation icon) {
        super(icon, new AttributeValuePair(Attribute.RANGE, 2.0F, 1.0F, 5.0F, 1.0F, 5.0F));
    }

    @Override
    public ComponentApplicationResult ApplyEffect(SpellSource source, SpellTarget spellTarget, IModifiedSpellPart<SpellEffect> modificationData, SpellContext spellContext) {
        if(spellContext.getWorld().isClientSide){
            return  ComponentApplicationResult.SUCCESS;
        }

        LivingEntity target = spellTarget.getLivingEntity();

        if(target == null){
            return ComponentApplicationResult.FAIL;
        }

        if (!this.magnitudeHealthCheck(source, spellTarget, 1, 60)) {
            return ComponentApplicationResult.FAIL;
        }

        TeleportHelper.randomTeleport(target, (modificationData.getValue(Attribute.RANGE) * 20), TRIES);

        return ComponentApplicationResult.SUCCESS;
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
        return 20.0F;
    }

    @Override
    public int requiredXPForRote() {
        return 150;
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
