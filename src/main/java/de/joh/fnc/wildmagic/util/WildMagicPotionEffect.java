package de.joh.fnc.wildmagic.util;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.FactionsAndCuriosities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Base class for Wild Magics, that give the Target a Spell effect
 * @see WildMagic
 * @author Joh0210
 */
public abstract class WildMagicPotionEffect extends WildMagicCOT {
    /**
     * How long will the effect last. (in Ticks)
     */
    public final int duration;

    /**
     * Which Level will the Effect have (staring at 1)
     */
    public final int level;

    /**
     * @return the effect that will be Applied
     */
    public @NotNull abstract MobEffect getMobEffect();

    /**
     * @param registryName ID under which the upgrade can be recognized
     * @param frequency How often does the entry appear in the random-selection-list?
     * @param duration How long will the effect last. (in Ticks)
     * @param level Which Level will the Effect have (staring at 1)
     * @param targetsCaster Is the wild Magic source(true) or the spellTarget(false) targeted?
     */
    public WildMagicPotionEffect(@NotNull ResourceLocation registryName, int frequency, boolean targetsCaster, int duration, int level) {
        super(registryName, frequency, targetsCaster);
        this.duration = duration;
        this.level = level;
    }

    @Override
    protected void performWildMagic(@NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull SpellPartTags spellPartTag) {
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

        wildMagicTarget.addEffect(new MobEffectInstance(getMobEffect(), duration, level - 1));
    }

    @Override
    public boolean canBePerformed(@NotNull LivingEntity source, @Nullable SpellTarget target) {
        if(!targetsCaster && (target == null || target.getLivingEntity() == null)){
            return false;
        }

        LivingEntity wildMagicTarget = targetsCaster ? source : target.getLivingEntity();

        return !wildMagicTarget.hasEffect(getMobEffect()) || wildMagicTarget.getEffect(getMobEffect()).getAmplifier() < (level - 1);
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        if(getMobEffect().getCategory() == MobEffectCategory.NEUTRAL){
            return Quality.NEUTRAL;
        }

        if(targetsCaster || componentTag != SpellPartTags.HARMFUL){
            return getMobEffect().getCategory() == MobEffectCategory.HARMFUL ? Quality.BAD : Quality.GOOD;
        }
        return getMobEffect().getCategory() == MobEffectCategory.HARMFUL ? Quality.GOOD : Quality.BAD;
    }

    @Override
    public boolean requiresSpellLivingTarget() {
        return !targetsCaster;
    }
}
