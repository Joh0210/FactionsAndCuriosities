package de.joh.fnc.common.effect.neutral;

import de.joh.fnc.common.event.CommonEventHandler;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * If the User Mines an ore, it will drop another random ore
 * @see CommonEventHandler
 * @author Joh0210
 */
public class RandomOreMinerMobEffect extends MobEffect {
    public RandomOreMinerMobEffect() {
        super(MobEffectCategory.NEUTRAL, 0x696969);
    }
}
