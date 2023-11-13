package de.joh.fnc.common.effect.harmful;

import de.joh.fnc.common.effect.beneficial.MaximizedMobEffect;
import de.joh.fnc.common.event.MagicEventHandler;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * Minimizes all Modifiers of a spell (except Delay)
 * <br>Counterpart to {@link MaximizedMobEffect}
 * @see MagicEventHandler
 * @author Joh0210
 */
public class MinimizedMobEffect extends MobEffect {
    public MinimizedMobEffect() {
        super(MobEffectCategory.HARMFUL, 0x008800);
    }
}
