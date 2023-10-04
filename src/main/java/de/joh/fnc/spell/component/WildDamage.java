package de.joh.fnc.spell.component;

import com.mna.api.affinity.Affinity;
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
import com.mna.config.GeneralModConfig;
import de.joh.fnc.utils.AttributeInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;

import java.util.List;
import java.util.Random;

/**
 * This Damage Spell causes a random amount of Damage.
 * <br> On average, it's a bit stronger, then other Damage Spells (+25%)
 * <br> The Precision Attribute ensures that the spell tends to do more average damage
 * <br> The Spell is effectd by the {@link AttributeInit#WILD_MAGIC_LUCK wild magic luck attribute}
 * @see AttributeInit
 * @author Joh0210
 */
public class WildDamage extends SpellEffect implements IDamageComponent {
    //todo: Make configurable
    private static final float FACTOR_MAX_DAMAGE_MULTIPLIER = 2.5F;

    public WildDamage(ResourceLocation registryName, ResourceLocation icon) {
        super(registryName, icon, new AttributeValuePair(Attribute.DAMAGE, 5.0F, 1.0F, 20.0F, 0.5F, 3.0F), new AttributeValuePair(Attribute.PRECISION, 1.0F, 1.0F, 4.0F, 1.0F, 5.0F));
    }

    public ComponentApplicationResult ApplyEffect(SpellSource source, SpellTarget target, IModifiedSpellPart<SpellEffect> modificationData, SpellContext context) {
        //todo: target geht't the wild Magic Effect? -> but average Damage back to 20!

        if(context.getWorld().isClientSide()){
            return ComponentApplicationResult.SUCCESS;
        }

        if (target.isEntity() && target.getEntity() instanceof LivingEntity entity) {
            Random random = new Random();
            float damage = 0;

            AttributeInstance modifierAttribute = source.getCaster() != null ? source.getCaster().getAttribute(AttributeInit.WILD_MAGIC_LUCK.get()) : null;

            boolean chooseHigher = modifierAttribute == null || modifierAttribute.getValue() >= 0;
            for(int j = 0; j < Math.max(modifierAttribute != null ? Math.abs((int)modifierAttribute.getValue()) + 1 : 1, 1); j++) {
                float alternativDamage = 0;

                for (int i = 0; i < Math.round(modificationData.getValue(Attribute.PRECISION)); i++) {
                    //With max Attribute.DAMAGE: Random between 1-50 -> ~25
                    alternativDamage += random.nextInt((int) (modificationData.getValue(Attribute.DAMAGE) * FACTOR_MAX_DAMAGE_MULTIPLIER)) + 1;
                }

                alternativDamage /= modificationData.getValue(Attribute.PRECISION);

                if(damage < alternativDamage == chooseHigher){
                    damage = alternativDamage;
                }
            }

            entity.hurt(createSourcedDamageType(source.getCaster()), damage * GeneralModConfig.getDamageMultiplier());



            return ComponentApplicationResult.SUCCESS;
        }

        return ComponentApplicationResult.FAIL;
    }

    @Override
    public boolean targetsBlocks() {
        return false;
    }

    @Override
    public SoundEvent SoundEffect() {
        return SFX.Spell.Impact.Single.EARTH;
    }

    @Override
    public Affinity getAffinity() {
        return Affinity.EARTH;
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

    private static DamageSource createSourcedDamageType(LivingEntity source) {
        EntityDamageSource copy_source = new EntityDamageSource(DamageSource.MAGIC.getMsgId(), source);

        copy_source.bypassArmor();
        copy_source.bypassMagic();

        return copy_source;
    }
}
