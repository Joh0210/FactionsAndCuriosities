package de.joh.fnc.common.wildmagic;

import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.api.wildmagic.WildMagicMCPotionEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gives the Player the Water Breathing Effect.
 * <br>The Target needs to be in water
 * @author Joh0210
 */
public class WaterBreathingWildMagic extends WildMagicMCPotionEffect {
    public WaterBreathingWildMagic(@NotNull ResourceLocation registryName, int frequency, boolean targetsCaster) {
        super(registryName, frequency, targetsCaster, MobEffects.WATER_BREATHING, 6000, 1);
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
