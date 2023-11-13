package de.joh.fnc.common.effect.beneficial;

import de.joh.fnc.common.effect.harmful.MinimizedMobEffect;
import de.joh.fnc.common.event.MagicEventHandler;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * Maximises all Modifiers of a spell (except Delay)
 * <br>Counterpart to {@link MinimizedMobEffect}
 * @see MagicEventHandler
 * @author Joh0210
 */
public class MaximizedMobEffect extends MobEffect {
    public MaximizedMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x00ffff);
    }
}
