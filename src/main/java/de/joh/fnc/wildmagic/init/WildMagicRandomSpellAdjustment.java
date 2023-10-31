package de.joh.fnc.wildmagic.init;

import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.effect.EffectInit;
import de.joh.fnc.event.handler.MagicEventHandler;
import de.joh.fnc.spelladjustment.SpellAdjustmentInit;
import de.joh.fnc.spelladjustment.util.SpellAdjustment;
import de.joh.fnc.wildmagic.util.WildMagicPotionEffect;
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
public class WildMagicRandomSpellAdjustment extends WildMagicPotionEffect {
    /**
     * @param registryName  ID under which the upgrade can be recognized
     * @param frequency     How often does the entry appear in the random-selection-list?
     */
    public WildMagicRandomSpellAdjustment(@NotNull ResourceLocation registryName, int frequency) {
        super(registryName, frequency, true, 1000000 , 1);
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
