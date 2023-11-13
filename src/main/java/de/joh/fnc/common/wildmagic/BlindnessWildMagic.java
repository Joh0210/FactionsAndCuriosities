package de.joh.fnc.common.wildmagic;

import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.api.wildmagic.WildMagicPotionEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Makes the target of wild magic blind for 30s
 * @author Joh0210
 */
public class BlindnessWildMagic extends WildMagicPotionEffect {
    /**
     * @param registryName  ID under which the upgrade can be recognized
     * @param frequency     How often does the entry appear in the random-selection-list?
     * @param targetsCaster Is the wild Magic source(true) or the spellTarget(false) targeted?
     */
    public BlindnessWildMagic(@NotNull ResourceLocation registryName, int frequency, boolean targetsCaster) {
        super(registryName, frequency, targetsCaster, 600, 1, true, false);
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
        return MobEffects.BLINDNESS;
    }
}
