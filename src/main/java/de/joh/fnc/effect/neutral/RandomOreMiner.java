package de.joh.fnc.effect.neutral;

import de.joh.fnc.event.handler.CommonEventHandler;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * If the User Mines an ore, it will drop another random ore
 * @see CommonEventHandler
 * @author Joh0210
 */
public class RandomOreMiner extends MobEffect {
    public RandomOreMiner() {
        super(MobEffectCategory.NEUTRAL, 0x696969);
    }
}
