package de.joh.fnc.effect.beneficial;

import de.joh.fnc.effect.harmful.Minimized;
import de.joh.fnc.event.handler.MagicEventHandler;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * Maximises all Modifiers of a spell (except Delay)
 * <br>Counterpart to {@link Minimized}
 * @see MagicEventHandler
 * @author Joh0210
 */
public class Maximized extends MobEffect {
    public Maximized() {
        super(MobEffectCategory.BENEFICIAL, 0x00ffff);
    }
}
