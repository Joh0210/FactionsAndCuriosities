package de.joh.fnc.common.effect.beneficial;

import de.joh.fnc.common.event.DamageEventHandler;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * Protects the Entity form any Damage caused by Explosions.
 * @see DamageEventHandler
 * @author Joh0210
 */
public class ExplosionResistanceMobEffect extends MobEffect {
    public ExplosionResistanceMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xa9a9a9);
    }
}