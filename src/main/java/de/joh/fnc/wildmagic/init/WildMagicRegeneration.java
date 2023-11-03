package de.joh.fnc.wildmagic.init;

import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.wildmagic.util.WildMagicMCPotionEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gives the regeneration effect to the target
 * <br>The target can maximally have 75% of its HP
 * @author Joh0210
 */
public class WildMagicRegeneration extends WildMagicMCPotionEffect {
    public WildMagicRegeneration(@NotNull ResourceLocation registryName, int frequency, boolean targetsCaster) {
        super(registryName, frequency, targetsCaster, MobEffects.REGENERATION, 1200, 1);
    }

    @Override
    public boolean canBePerformed(@NotNull LivingEntity source, @Nullable SpellTarget target) {
        if(targetsCaster || (target != null && target.getLivingEntity() != null)) {
            LivingEntity entity = targetsCaster ? source : target.getLivingEntity();
            return entity.getHealth() <= entity.getMaxHealth() * 0.75f && super.canBePerformed(source, target);
        }
        return false;
    }
}
