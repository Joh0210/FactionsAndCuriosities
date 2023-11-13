package de.joh.fnc.common.wildmagic;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.api.util.Quality;
import de.joh.fnc.api.wildmagic.WildMagicCOT;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Deals damage equal to half the Max HP of the Target.
 * <br>The target needs to have at least 70% of its HP and the max Damage can be limited
 */
public class ReduceHPWildMagic extends WildMagicCOT {
    private final int maxDamage;
    private final float percentage;

    /**
     * @param registryName  ID under which the upgrade can be recognized
     * @param frequency     How often does the entry appear in the random-selection-list?
     * @param targetsCaster Is the wild Magic source(true) or the spellTarget(false) targeted?
     * @param percentage    What percentage of Max HP is deducted?
     * @param maxDamage     The maximum amount of Damage
     */
    public ReduceHPWildMagic(@NotNull ResourceLocation registryName, int frequency, boolean targetsCaster, float percentage, int maxDamage) {
        super(registryName, frequency, targetsCaster);
        this.percentage = Math.min(percentage, 0.5f);
        this.maxDamage = maxDamage;
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        if(targetsCaster || componentTag != SpellPartTags.HARMFUL){
            return Quality.VERY_BAD;
        }
        return Quality.VERY_GOOD;
    }

    @Override
    protected void performWildMagic(@NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull SpellPartTags spellPartTag) {
        if(targetsCaster || (target != null && target.getLivingEntity() != null)) {
            LivingEntity entity = targetsCaster ? source : target.getLivingEntity();
            entity.hurt(DamageSource.MAGIC, Math.min(maxDamage, entity.getMaxHealth() * percentage));
        }
    }

    @Override
    public boolean canBePerformed(@NotNull LivingEntity source, @Nullable SpellTarget target) {
        if(targetsCaster || (target != null && target.getLivingEntity() != null)) {
            LivingEntity entity = targetsCaster ? source : target.getLivingEntity();
            return entity.getHealth() >= entity.getMaxHealth() * percentage * 1.5f;
        }
        return false;
    }

    @Override
    public boolean requiresSpellLivingTarget() {
        return !targetsCaster;
    }
}
