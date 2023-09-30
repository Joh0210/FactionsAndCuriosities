package de.joh.fnc.wildmagic.util;

import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.FactionsAndCuriosities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Base class for Wild Magics, that give the Target a Spell effect
 * @see WildMagic
 * @author Joh0210
 */
public abstract class WildMagicPotionEffect extends WildMagic{
    /**
     * How long will the effect last. (in Ticks)
     */
    public final int duration;

    /**
     * Which Level will the Effect have (staring at 1)
     */
    public final int level;

    /**
     * Is the wild Magic source(true) or the spellTarget(false) targeted?
     */
    public final boolean targetsCaster;

    /**
     * @return the effect that will be Applied
     */
    public abstract MobEffect getMobEffect();

    public WildMagicPotionEffect(@NotNull ResourceLocation registryName, int frequency, int duration, int level, boolean targetsCaster) {
        super(registryName, frequency);
        this.duration = duration;
        this.level = level;
        this.targetsCaster = targetsCaster;
    }

    @Override
    public boolean requiresSpellLivingTarget() {
        return !targetsCaster;
    }

    @Override
    public void performWildMagic(@NotNull LivingEntity source, @Nullable SpellTarget target) {
        if(!targetsCaster && target == null){
            //todo: throw exception?
            FactionsAndCuriosities.LOGGER.error("tried performWildMagic on the Target with a null Target");
            return;
        }

        LivingEntity wildMagicTarget = targetsCaster ? source : target.getLivingEntity();

        if(wildMagicTarget == null){
            //todo: throw exception?
            FactionsAndCuriosities.LOGGER.error("target.getLivingEntity() of performWildMagic is null but was used");
            return;
        }

        if(!wildMagicTarget.hasEffect(getMobEffect()) || wildMagicTarget.getEffect(getMobEffect()).getAmplifier() < (level - 1)){
            wildMagicTarget.addEffect(new MobEffectInstance(getMobEffect(), duration, level - 1));
        }
        else{
            //Update the duration of the effect.
            wildMagicTarget.getEffect(getMobEffect()).update(new MobEffectInstance(getMobEffect(), duration, level - 1));
        }
    }
}
