package de.joh.fnc.common.spell.component;

import com.mna.api.affinity.Affinity;
import com.mna.api.config.GeneralConfigValues;
import com.mna.api.entities.DamageHelper;
import com.mna.api.faction.IFaction;
import com.mna.api.sound.SFX;
import com.mna.api.spells.ComponentApplicationResult;
import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.attributes.Attribute;
import com.mna.api.spells.attributes.AttributeValuePair;
import com.mna.api.spells.base.IDamageComponent;
import com.mna.api.spells.base.IModifiedSpellPart;
import com.mna.api.spells.parts.SpellEffect;
import com.mna.api.spells.targeting.SpellContext;
import com.mna.api.spells.targeting.SpellSource;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.api.wildmagic.WildMagicHelper;
import de.joh.fnc.common.init.EffectInit;
import de.joh.fnc.common.init.FactionInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;
import java.util.Random;

/**
 * This Damage Spell causes a random amount of Damage.
 * <br> The Precision Attribute ensures that the spell tends to do more average damage
 * <br> The Target of the spell will be cursed with the (Bad-) Wild Magic Effect
 * <br> The Spell is effected by {@link WildMagicHelper#getWildMagicLuck(LivingEntity) WildMagicHelper.getWildMagicLuck()}
 * @see WildMagicHelper
 * @author Joh0210
 */
public class WildDamageComponent extends SpellEffect implements IDamageComponent {

    public WildDamageComponent(ResourceLocation icon) {
        super(icon, new AttributeValuePair(Attribute.DAMAGE, 10.0F, 2.0F, 40.0F, 1F, 3.0F), new AttributeValuePair(Attribute.MAGNITUDE, 1.0F, 0.0F, 3.0F, 1.0F, 15.0F), new AttributeValuePair(Attribute.DURATION, 30.0F, 15.0F, 300.0F, 15.0F, 3.0F), new AttributeValuePair(Attribute.PRECISION, 1.0F, 1.0F, 4.0F, 1.0F, 5.0F));
    }

    public ComponentApplicationResult ApplyEffect(SpellSource source, SpellTarget target, IModifiedSpellPart<SpellEffect> modificationData, SpellContext context) {
        if(context.getLevel().isClientSide()){
            return ComponentApplicationResult.SUCCESS;
        }

        if (target.isEntity() && target.getEntity() instanceof LivingEntity entity) {
            Random random = new Random();
            float damage = 0;

            boolean chooseHigher = source.getCaster() == null || WildMagicHelper.getWildMagicLuck(source.getCaster()) >= 0;
            for(int j = 0; j < (source.getCaster() != null ? Math.abs(WildMagicHelper.getWildMagicLuck(source.getCaster())) + 1 : 1); j++) {
                float alternativDamage = 0;

                for (int i = 0; i < Math.round(modificationData.getValue(Attribute.PRECISION)); i++) {
                    //With max Attribute.DAMAGE: Random between 1-40 -> ~20
                    alternativDamage += random.nextInt((int) (modificationData.getValue(Attribute.DAMAGE))) + 1;
                }

                alternativDamage /= modificationData.getValue(Attribute.PRECISION);

                if(damage < alternativDamage == chooseHigher || damage < 1.0f){
                    damage = alternativDamage;
                }
            }

            entity.hurt(DamageHelper.createSourcedType(DamageTypes.MAGIC, context.getLevel().registryAccess(), source.getCaster()), damage * Math.max((float) GeneralConfigValues.GlobalDamageScale, 0.1f));

            if((int)modificationData.getValue(Attribute.MAGNITUDE) >= 1) {
                entity.addEffect(new MobEffectInstance(
                        (int) modificationData.getValue(Attribute.MAGNITUDE) >= 2 ? EffectInit.BAD_WILD_MAGIC.get() : EffectInit.WILD_MAGIC.get(),
                        (int) modificationData.getValue(Attribute.DURATION) * 20,
                        Math.max((int) modificationData.getValue(Attribute.MAGNITUDE) - 2, 0)));
            }
            return ComponentApplicationResult.SUCCESS;
        }

        return ComponentApplicationResult.FAIL;
    }

    @Override
    public boolean targetsBlocks() {
        return false;
    }

    @Override
    public IFaction getFactionRequirement() {
        return FactionInit.WILD;
    }

    @Override
    public SoundEvent SoundEffect() {
        return SFX.Spell.Impact.Single.EARTH;
    }

    @Override
    public Affinity getAffinity() {
        return Affinity.ARCANE;
    }

    @Override
    public float initialComplexity() {
        return 10.0F;
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
    public List<Affinity> getValidTinkerAffinities() {
        return List.of(Affinity.ARCANE);
    }
}
