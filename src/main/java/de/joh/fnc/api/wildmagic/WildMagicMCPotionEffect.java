package de.joh.fnc.api.wildmagic;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import org.jetbrains.annotations.NotNull;

/**
 * Base class for Wild Magics, that give the Target a Spell effect
 * @see WildMagic
 * @author Joh0210
 */
public class WildMagicMCPotionEffect extends WildMagicPotionEffect {
    private final MobEffect effect;

    @Override
    public @NotNull MobEffect getMobEffect() {
        return effect;
    }

    /**
     * @param registryName  ID under which the upgrade can be recognized
     * @param frequency     How often does the entry appear in the random-selection-list?
     * @param targetsCaster Is the wild Magic source(true) or the spellTarget(false) targeted?
     * @param effect        Effect for the target (only MC Effects)
     * @param duration      How long will the effect last. (in Ticks)
     * @param level         Which Level will the Effect have (staring at 1)
     */
    public WildMagicMCPotionEffect(@NotNull ResourceLocation registryName, int frequency, boolean targetsCaster, MobEffect effect, int duration, int level) {
        this(registryName, frequency, targetsCaster, effect, duration, level, false, false);
    }

    /**
     * @param registryName  ID under which the upgrade can be recognized
     * @param frequency     How often does the entry appear in the random-selection-list?
     * @param targetsCaster Is the wild Magic source(true) or the spellTarget(false) targeted?
     * @param effect        Effect for the target (only MC Effects)
     * @param duration      How long will the effect last. (in Ticks)
     * @param level         Which Level will the Effect have (staring at 1)
     * @param extremismQuality true: Bad -> Very Bad; Good -> Very Good
     * @param extremeOnlyNotHarmful true: Bad -> Very Bad; Good -> Very Good only on the friendly side
     */
    public WildMagicMCPotionEffect(@NotNull ResourceLocation registryName, int frequency, boolean targetsCaster, MobEffect effect, int duration, int level, boolean extremismQuality, boolean extremeOnlyNotHarmful) {
        super(registryName, frequency, targetsCaster, duration, level, extremismQuality, extremeOnlyNotHarmful);
        this.effect = effect;
    }
}
