package de.joh.fnc.common.effect.smite;

import de.joh.fnc.api.smite.SmiteMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.List;

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
    private static final List<MobEffect> EFFECTS = List.of(
            MobEffects.WEAKNESS,
            MobEffects.MOVEMENT_SLOWDOWN
    );

    public CripplingSmite() {
        super(0x718000 );
    }

    @Override
    public void performSmite(Player source, LivingEntity target, int range, int magnitude, int duration, int precision) {
        for(MobEffect effect : EFFECTS){
            target.addEffect(new MobEffectInstance(effect, duration * 20, magnitude - 1, false, false, false));
        }
    }
}
