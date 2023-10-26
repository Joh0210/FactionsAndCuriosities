package de.joh.fnc.effect.harmful;

import de.joh.fnc.effect.beneficial.Maximized;
import de.joh.fnc.event.handler.MagicEventHandler;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * Minimizes all Modifiers of a spell (except Delay)
 * <br>Counterpart to {@link Maximized}
 * @see MagicEventHandler
 * @author Joh0210
 */
public class Minimized extends MobEffect {
    public Minimized() {
        super(MobEffectCategory.HARMFUL, 0x008800);
    }
}
