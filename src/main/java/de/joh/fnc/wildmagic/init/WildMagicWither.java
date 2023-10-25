package de.joh.fnc.wildmagic.init;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.wildmagic.util.Quality;
import de.joh.fnc.wildmagic.util.WildMagicPotionEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Poisons the target of wild magic for 20s
 * @author Joh0210
 */
public class WildMagicWither extends WildMagicPotionEffect {
    /**
     * @param registryName  ID under which the upgrade can be recognized
     * @param frequency     How often does the entry appear in the random-selection-list?
     * @param targetsCaster Is the wild Magic source(true) or the spellTarget(false) targeted?
     * @param level         Which Level will the Effect have (staring at 1)
     */
    public WildMagicWither(@NotNull ResourceLocation registryName, int frequency, boolean targetsCaster, int level) {
        super(registryName, frequency, targetsCaster, 400, level);
    }

    @Override
    public boolean canBePerformed(@NotNull LivingEntity source, @Nullable SpellTarget target) {
        if(targetsCaster){
            if(!(source instanceof Player)){
                return false;
            }
        } else {
            if(target == null || !(target.getLivingEntity() instanceof Player)){
                return false;
            }
        }

        return super.canBePerformed(source, target);
    }

    @Override
    public @NotNull MobEffect getMobEffect() {
        return MobEffects.CONFUSION;
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        if(targetsCaster || componentTag != SpellPartTags.HARMFUL){
            return level > 1 ? Quality.VERY_BAD : Quality.BAD;
        }
        return Quality.GOOD;
    }
}
