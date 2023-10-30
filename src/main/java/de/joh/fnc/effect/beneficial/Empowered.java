package de.joh.fnc.effect.beneficial;

import de.joh.fnc.event.handler.MagicEventHandler;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * Increases all Modifiers of a spell (except Delay and Precision) by one step per Level. (Beyond Maximum)
 * @see MagicEventHandler
 * @author Joh0210
 */
public class Empowered extends MobEffect {
    public Empowered() {
        super(MobEffectCategory.BENEFICIAL, 9643043);
    }
}
