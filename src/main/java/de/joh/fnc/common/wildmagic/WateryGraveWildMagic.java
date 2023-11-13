package de.joh.fnc.common.wildmagic;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;

import com.mna.effects.EffectInit;
import de.joh.fnc.api.util.Quality;
import de.joh.fnc.api.wildmagic.WildMagicPotionEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The Target can no longer swim for 30s
 * <br>The Target needs to be in water
 * @author Joh0210
 */
public class WateryGraveWildMagic extends WildMagicPotionEffect {
    public WateryGraveWildMagic(@NotNull ResourceLocation registryName, int frequency, boolean targetsCaster) {
        super(registryName, frequency, targetsCaster, 600, 1, true, true);
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        if((targetsCaster || componentTag != SpellPartTags.HARMFUL)
                == (getMobEffect().getCategory() == MobEffectCategory.HARMFUL)){
            return Quality.VERY_BAD;
        } else {
            return Quality.GOOD;
        }
    }

    @Override
    public @NotNull MobEffect getMobEffect() {
        return EffectInit.WATERY_GRAVE.get();
    }

    @Override
    public boolean canBePerformed(@NotNull LivingEntity source, @Nullable SpellTarget target) {
        if(!targetsCaster && (target == null || target.getLivingEntity() == null)){
            return false;
        }

        if(!(targetsCaster ? source : target.getLivingEntity()).isInWater()){
            return false;
        }

        return super.canBePerformed(source, target);
    }
}
