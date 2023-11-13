package de.joh.fnc.common.wildmagic;

import com.mna.api.spells.SpellPartTags;
import de.joh.fnc.api.util.Quality;
import de.joh.fnc.api.wildmagic.WildMagicPotionEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import org.jetbrains.annotations.NotNull;

/**
 * Poisons the target of wild magic for 20s
 * @author Joh0210
 */
public class WitherWildMagic extends WildMagicPotionEffect {
    /**
     * @param registryName  ID under which the upgrade can be recognized
     * @param frequency     How often does the entry appear in the random-selection-list?
     * @param targetsCaster Is the wild Magic source(true) or the spellTarget(false) targeted?
     * @param level         Which Level will the Effect have (staring at 1)
     */
    public WitherWildMagic(@NotNull ResourceLocation registryName, int frequency, boolean targetsCaster, int level) {
        super(registryName, frequency, targetsCaster, 400, level);
    }

    @Override
    public @NotNull MobEffect getMobEffect() {
        return MobEffects.WITHER;
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        if(targetsCaster || componentTag != SpellPartTags.HARMFUL){
            return level > 1 ? Quality.VERY_BAD : Quality.BAD;
        }
        return Quality.GOOD;
    }
}
