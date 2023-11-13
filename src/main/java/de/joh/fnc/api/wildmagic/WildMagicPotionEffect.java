package de.joh.fnc.api.wildmagic;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.api.util.Quality;
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
    private final boolean extremismQuality;

    private final boolean extremeOnlyNotHarmful;

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
     * @param extremismQuality true: Bad -> Very Bad; Good -> Very Good
     * @param extremeOnlyNotHarmful true: Bad -> Very Bad; Good -> Very Good only on the friendly side
     */
    public WildMagicPotionEffect(@NotNull ResourceLocation registryName, int frequency, boolean targetsCaster, int duration, int level, boolean extremismQuality, boolean extremeOnlyNotHarmful) {
        super(registryName, frequency, targetsCaster);
        this.duration = duration;
        this.level = level;
        this.extremismQuality = extremismQuality;
        this.extremeOnlyNotHarmful = extremeOnlyNotHarmful && extremismQuality;
    }

    /**
     * @param registryName ID under which the upgrade can be recognized
     * @param frequency How often does the entry appear in the random-selection-list?
     * @param duration How long will the effect last. (in Ticks)
     * @param level Which Level will the Effect have (staring at 1)
     * @param targetsCaster Is the wild Magic source(true) or the spellTarget(false) targeted?
     */
    public WildMagicPotionEffect(@NotNull ResourceLocation registryName, int frequency, boolean targetsCaster, int duration, int level) {
        this(registryName, frequency, targetsCaster, duration, level, false, false);
    }

    @Override
    protected void performWildMagic(@NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull SpellPartTags spellPartTag) {
        if(!targetsCaster && target == null){
            return;
        }

        LivingEntity wildMagicTarget = targetsCaster ? source : target.getLivingEntity();

        if(wildMagicTarget == null){
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

        if((targetsCaster || componentTag != SpellPartTags.HARMFUL)
                == (getMobEffect().getCategory() == MobEffectCategory.HARMFUL)){
            return extremismQuality ? Quality.VERY_BAD : Quality.BAD;
        } else {
            return extremismQuality && !extremeOnlyNotHarmful ? Quality.VERY_GOOD : Quality.GOOD;
        }
    }

    @Override
    public boolean requiresSpellLivingTarget() {
        return !targetsCaster;
    }
}
