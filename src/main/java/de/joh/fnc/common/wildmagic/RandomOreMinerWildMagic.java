package de.joh.fnc.common.wildmagic;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.common.effect.neutral.RandomOreMinerMobEffect;
import de.joh.fnc.common.init.EffectInit;
import de.joh.fnc.api.util.Quality;
import de.joh.fnc.api.wildmagic.WildMagicPotionEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gives the Target the {@link RandomOreMinerMobEffect RandomOreMiner Effect} for 5 min
 * @author Joh0210
 */
public class RandomOreMinerWildMagic extends WildMagicPotionEffect {
    /**
     * @param registryName  ID under which the upgrade can be recognized
     * @param frequency     How often does the entry appear in the random-selection-list?
     */
    public RandomOreMinerWildMagic(@NotNull ResourceLocation registryName, int frequency) {
        super(registryName, frequency, true, 3000, 1);
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        return Quality.NEUTRAL;
    }

    @Override
    public boolean canBePerformed(@NotNull LivingEntity source, @Nullable SpellTarget target) {
        if(!(source instanceof Player)){
            return false;
        }

        return super.canBePerformed(source, target);
    }

    @Override
    public @NotNull MobEffect getMobEffect() {
        return EffectInit.RANDOM_ORE_MINER.get();
    }
}
