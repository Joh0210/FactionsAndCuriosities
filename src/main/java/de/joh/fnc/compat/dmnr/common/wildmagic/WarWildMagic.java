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
public class WarWildMagic extends WildMagicOtherPotionEffect {
    public WarWildMagic(@NotNull ResourceLocation registryName, int frequency, int duration) {
        super(registryName, frequency, true, EffectInit.BROKEN_PEACE_EFFECT, duration, 1);
    }

    @Override
    protected void performWildMagic(@NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull SpellPartTags spellPartTag) {
        source.removeEffect(EffectInit.PEACE_EFFECT.get());
        super.performWildMagic(source, target, spellPartTag);
    }

    @Override
    public boolean canBePerformed(@NotNull LivingEntity source, @Nullable SpellTarget target) {
        if(source.hasEffect(EffectInit.PEACE_EFFECT.get())){
            return super.canBePerformed(source, target);
        }

        return false;
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        return Quality.VERY_BAD;
    }
}
