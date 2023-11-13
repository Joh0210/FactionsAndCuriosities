package de.joh.fnc.common.effect.beneficial;

import de.joh.fnc.common.event.MagicEventHandler;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * Increases all Modifiers of a spell (except Delay and Precision) by one step per Level. (Beyond Maximum)
 * @see MagicEventHandler
 * @author Joh0210
 */
public class EmpoweredMobEffect extends MobEffect {
    public EmpoweredMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 9643043);
    }
}
