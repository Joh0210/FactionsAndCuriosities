package de.joh.fnc.compat.dmnr.common.wildmagic;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.dragonmagicandrelics.effects.EffectInit;
import de.joh.fnc.api.util.Quality;
import de.joh.fnc.api.wildmagic.WildMagicOtherPotionEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Declares Peace with the other Factions for the duration, as long as there they are not in war
 * @author Joh0210
 */
public class PeaceWildMagic extends WildMagicOtherPotionEffect {
    public PeaceWildMagic(@NotNull ResourceLocation registryName, int frequency, int duration) {
        super(registryName, frequency, true, EffectInit.PEACE_EFFECT, duration, 1);
    }

    @Override
    public boolean canBePerformed(@NotNull LivingEntity source, @Nullable SpellTarget target) {
        if(source.hasEffect(EffectInit.BROKEN_PEACE_EFFECT.get())){
            return false;
        }

        return super.canBePerformed(source, target);
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        return Quality.GOOD;
    }
}
