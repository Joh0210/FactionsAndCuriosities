package de.joh.fnc.wildmagic.init;

import com.mna.api.spells.SpellPartTags;
import de.joh.fnc.wildmagic.util.Quality;
import de.joh.fnc.wildmagic.util.WildMagicPotionEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import org.jetbrains.annotations.NotNull;

/**
 * Poisons the target of wild magic for 30s * durationMod
 * @author Joh0210
 */
public class WildMagicPoison extends WildMagicPotionEffect {
    /**
     * @param registryName  ID under which the upgrade can be recognized
     * @param frequency     How often does the entry appear in the random-selection-list?
     * @param targetsCaster Is the wild Magic source(true) or the spellTarget(false) targeted?
     * @param durationMod   Duration = 30s * durationMod
     */
    public WildMagicPoison(@NotNull ResourceLocation registryName, int frequency, boolean targetsCaster, int durationMod) {
        super(registryName, frequency, targetsCaster, durationMod * 600, 1);
    }

    @Override
    public @NotNull MobEffect getMobEffect() {
        return MobEffects.POISON;
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        if(targetsCaster || componentTag != SpellPartTags.HARMFUL){
            return duration > 600 ? Quality.VERY_BAD : Quality.BAD;
        }
        return Quality.GOOD;
    }
}
