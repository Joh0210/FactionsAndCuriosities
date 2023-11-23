package de.joh.fnc.common.effect.smite;

import de.joh.fnc.api.smite.SmiteMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.Map;

/**
 * Smite, hat cripples the Target for a defined duration (max 20s).
 * <br>The Crippled target will be effected by these {@link CripplingSmite#EFFECTS effects}
 * @author Joh0210
 */
public class CripplingSmite extends SmiteMobEffect {
    /**
     * Defines the Effects, and their max level that the target will get, when it will be effected by the Smite
     * <br>todo: Adjust
     */
    private static final Map<MobEffect, Integer> EFFECTS = Map.of(
            MobEffects.BLINDNESS, 1,
            MobEffects.WEAKNESS, 1,
            MobEffects.MOVEMENT_SLOWDOWN, 3
    );

    public CripplingSmite() {
        super(0x718000 );
    }

    @Override
    public void performSmite(Player source, LivingEntity target, int range, int magnitude, int duration, int precision) {
        for(Map.Entry<MobEffect, Integer> entry : EFFECTS.entrySet()){
            if (target.hasEffect(entry.getKey()) && target.getEffect(entry.getKey()).getAmplifier() >= Math.min(magnitude, entry.getValue()) - 1) {
                target.getEffect(entry.getKey()).update(new MobEffectInstance(entry.getKey(), duration * 20, Math.min(magnitude, entry.getValue()) - 1, false, false, false));
            } else {
                target.addEffect(new MobEffectInstance(entry.getKey(), duration * 20, Math.min(magnitude, entry.getValue()) - 1, false, false, false));
            }
        }
    }
}
