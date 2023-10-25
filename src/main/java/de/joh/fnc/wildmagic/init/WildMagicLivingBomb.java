package de.joh.fnc.wildmagic.init;

import com.mna.api.spells.SpellPartTags;
import de.joh.fnc.effect.EffectInit;
import de.joh.fnc.wildmagic.util.Quality;
import de.joh.fnc.wildmagic.util.WildMagicPotionEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import org.jetbrains.annotations.NotNull;

/**
 * Lets the Target explode every 10s
 * @author Joh0210
 */
public class WildMagicLivingBomb extends WildMagicPotionEffect {
    /**
     * @param registryName  ID under which the upgrade can be recognized
     * @param frequency     How often does the entry appear in the random-selection-list?
     * @param targetsCaster Is the wild Magic source(true) or the spellTarget(false) targeted?
     * @param durationMod    duration = 600 * durationMod. (in Ticks)
     */
    public WildMagicLivingBomb(@NotNull ResourceLocation registryName, int frequency, boolean targetsCaster, int durationMod) {
        super(registryName, frequency, targetsCaster, 600 * durationMod, 1);
    }

    @Override
    public @NotNull MobEffect getMobEffect() {
        return EffectInit.LIVING_BOMB.get();
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        if(targetsCaster || componentTag != SpellPartTags.HARMFUL){
            return Quality.VERY_BAD;
        }
        return Quality.GOOD;
    }
}
