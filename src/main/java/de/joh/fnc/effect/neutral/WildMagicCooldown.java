package de.joh.fnc.effect.neutral;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * With this effect, an Entity cannot be the cause of Wild Magic
 * @author Joh0210
 */
public class WildMagicCooldown extends MobEffect {
    /**
     * How many ticks should be waited, till Wild Magic can be applied again?
     * <br>todo: make it adjustable
     */
    public static final int WILD_MAGIC_COOLDOWN = 100;

    public WildMagicCooldown() {
        super(MobEffectCategory.NEUTRAL, 0);
    }
}
