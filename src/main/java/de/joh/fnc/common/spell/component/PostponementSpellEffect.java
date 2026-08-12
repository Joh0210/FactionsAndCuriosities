package de.joh.fnc.common.spell.component;

import com.mna.api.affinity.Affinity;
import com.mna.api.faction.IFaction;
import com.mna.api.sound.SFX;
import com.mna.api.spells.ComponentApplicationResult;
import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.attributes.Attribute;
import com.mna.api.spells.attributes.AttributeValuePair;
import com.mna.api.spells.base.IModifiedSpellPart;
import com.mna.api.spells.parts.SpellEffect;
import com.mna.api.spells.targeting.SpellContext;
import com.mna.api.spells.targeting.SpellSource;
import com.mna.api.spells.targeting.SpellTarget;
import com.mna.effects.EffectInit;
import de.joh.fnc.common.init.FactionInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class PostponementSpellEffect extends SpellEffect {
    public PostponementSpellEffect(ResourceLocation guiIcon) {
        super(guiIcon, new AttributeValuePair(Attribute.DURATION, 8.0F, 5.0F, 16.0F, 1.0F, 10.0F));
        this.addReagent(new ItemStack(Items.CLOCK), false, true, true, new IFaction[0]);
    }

    public ComponentApplicationResult ApplyEffect(SpellSource source, SpellTarget target, IModifiedSpellPart<SpellEffect> modificationData, SpellContext context) {
        if (target.isLivingEntity() && target.getLivingEntity().getEffect(EffectInit.CHRONO_EXHAUSTION.get()) == null && target.getLivingEntity().getEffect(EffectInit.CHRONO_ANCHOR.get()) == null) {
            target.getLivingEntity().addEffect(new MobEffectInstance(de.joh.fnc.common.init.EffectInit.POSTPONEMENT.get(), (int)modificationData.getValue(Attribute.DURATION) * 20, 0, false, false));
            return ComponentApplicationResult.SUCCESS;
        } else {
            return ComponentApplicationResult.FAIL;
        }
    }

    public SoundEvent SoundEffect() {
        return SFX.Spell.Buff.BLOOD;
    }

    @Override
    public Affinity getAffinity() {
        return Affinity.BLOOD;
    }

    @Override
    public float initialComplexity() {
        return 30.0F;
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
    public boolean targetsBlocks() {
        return false;
    }
}
