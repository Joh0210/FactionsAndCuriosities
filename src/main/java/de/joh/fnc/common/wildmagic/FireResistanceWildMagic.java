package de.joh.fnc.common.wildmagic;

import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.api.wildmagic.WildMagicMCPotionEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FireResistanceWildMagic extends WildMagicMCPotionEffect {
    public FireResistanceWildMagic(@NotNull ResourceLocation registryName, int frequency, boolean targetsCaster) {
        super(registryName, frequency, targetsCaster, MobEffects.FIRE_RESISTANCE, 6000, 1, true, false);
    }

    @Override
    public boolean canBePerformed(@NotNull LivingEntity source, @Nullable SpellTarget target) {
        if(!targetsCaster && (target == null || target.getLivingEntity() == null)){
            return false;
        }

        if(!(targetsCaster ? source : target.getLivingEntity()).isOnFire()){
            return false;
        }

        return super.canBePerformed(source, target);
    }
}
