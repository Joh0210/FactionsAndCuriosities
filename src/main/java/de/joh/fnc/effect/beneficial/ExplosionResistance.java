package de.joh.fnc.effect.beneficial;

import de.joh.fnc.event.handler.DamageEventHandler;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * Protects the Entity form any Damage caused by Explosions.
 * @see DamageEventHandler
 * @author Joh0210
 */
public class ExplosionResistance extends MobEffect {
    public ExplosionResistance() {
        super(MobEffectCategory.BENEFICIAL, 0xa9a9a9);
    }
}