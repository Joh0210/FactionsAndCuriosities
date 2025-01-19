package de.joh.fnc.common.wildmagic;

import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.common.init.EffectInit;
import de.joh.fnc.common.event.MagicEventHandler;
import de.joh.fnc.common.init.SpellAdjustmentInit;
import de.joh.fnc.api.spelladjustment.SpellAdjustment;
import de.joh.fnc.api.wildmagic.WildMagicPotionEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The Next Spell casted will be altered with a random Spell Adjustment.
 * @see SpellAdjustment
 * @see MagicEventHandler
 * @see SpellAdjustmentInit
 * @author Joh0210
 */
public class RandomSpellAdjustmentWildMagic extends WildMagicPotionEffect {
    /**
     * @param registryName  ID under which the upgrade can be recognized
     * @param frequency     How often does the entry appear in the random-selection-list?
     */
    public RandomSpellAdjustmentWildMagic(@NotNull ResourceLocation registryName, int frequency) {
        super(registryName, frequency, true, -1 , 1);
    }

    @Override
    public boolean canBePerformed(@NotNull LivingEntity source, @Nullable SpellTarget target) {
        return source instanceof Player;
    }

    @Override
    public @NotNull MobEffect getMobEffect() {
        return EffectInit.RANDOM_SPELL_ADJUSTMENT.get();
    }
}
