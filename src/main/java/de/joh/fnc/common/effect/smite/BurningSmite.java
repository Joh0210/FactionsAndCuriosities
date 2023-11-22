package de.joh.fnc.common.effect.smite;

import de.joh.fnc.api.smite.SmiteMobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/**
 * Smite, that sets the target on fire for the given Duration
 * @author Joh0210
 */
public class BurningSmite extends SmiteMobEffect {
    public BurningSmite() {
        super(0xff8800);
    }

    @Override
    public void performSmite(Player source, LivingEntity target, int range, int magnitude, int duration, int precision) {
        target.setSecondsOnFire(duration);
    }
}
