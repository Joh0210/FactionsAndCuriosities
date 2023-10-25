package de.joh.fnc.wildmagic.init;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import com.mna.effects.EffectInit;
import de.joh.fnc.wildmagic.util.Quality;
import de.joh.fnc.wildmagic.util.WildMagicPotionEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This Wild Magic gives the Player an Effect.
 * When the Effect runs out, or if the Player dies, it teleports back to the place where it got the effect with the HP it had back then.
 * <br> Can only be Performed by Players
 * @author Joh0210
 */
public class WildMagicChronoAnchor extends WildMagicPotionEffect {
    /**
     * @param registryName  ID under which the upgrade can be recognized
     * @param frequency     How often does the entry appear in the random-selection-list?
     */
    public WildMagicChronoAnchor(@NotNull ResourceLocation registryName, int frequency) {
        super(registryName, frequency, true, 2400, 1);
    }

    @Override
    public boolean canBePerformed(@NotNull LivingEntity source, @Nullable SpellTarget target) {
        if(!(source instanceof Player) || source.hasEffect(EffectInit.CHRONO_EXHAUSTION.get())){
            return false;
        }

        return super.canBePerformed(source, target);
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        //todo: adjust!
        return Quality.NEUTRAL;
    }

    @Override
    public @NotNull MobEffect getMobEffect() {
        //todo: adjust!
        return EffectInit.CHRONO_ANCHOR.get();
    }
}
