package de.joh.fnc.common.effect.smite;

import com.mna.effects.EffectInit;
import de.joh.fnc.api.smite.SmiteMobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/**
 * Prevents the target from using spells
 * @author Joh0210
 */
public class SilencingSmite extends SmiteMobEffect {
    public SilencingSmite() {
        super(0xC0C0C0);
    }

    @Override
    public void performSmite(Player source, LivingEntity target, int range, int magnitude, int duration, int precision) {
        if (target.hasEffect(EffectInit.SILENCE.get())) {
            target.getEffect(EffectInit.SILENCE.get()).update(new MobEffectInstance(EffectInit.SILENCE.get(), duration * 20, 0, false, false, false));
        } else {
            target.addEffect(new MobEffectInstance(EffectInit.SILENCE.get(), duration * 20, 0, false, false, false));
        }
    }
}
