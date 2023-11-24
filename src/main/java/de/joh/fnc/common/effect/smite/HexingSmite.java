package de.joh.fnc.common.effect.smite;


import de.joh.fnc.api.smite.SmiteMobEffect;
import de.joh.fnc.common.effect.harmful.HexMobEffect;
import de.joh.fnc.common.init.EffectInit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/**
 * Applies the Hex Effect on the Target
 * @see HexMobEffect
 * @author Joh0210
 */
public class HexingSmite extends SmiteMobEffect {
    public HexingSmite() {
        super(0x000000);
    }

    @Override
    public void performSmite(Player source, LivingEntity target, int range, int magnitude, int duration, int precision) {
        if (target.hasEffect(EffectInit.HEX.get()) && target.getEffect(EffectInit.HEX.get()).getAmplifier() >= magnitude - 1) {
            target.getEffect(EffectInit.HEX.get()).update(new MobEffectInstance(EffectInit.HEX.get(), duration * 20, magnitude-1, false, false, false));
        } else {
            target.addEffect(new MobEffectInstance(EffectInit.HEX.get(), duration * 20, magnitude-1, false, false, false));
        }
    }
}
