package de.joh.fnc.common.effect.smite;

import de.joh.fnc.api.smite.SmiteMobEffect;
import de.joh.fnc.common.util.CommonConfig;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;

import java.util.ArrayList;

/**
 * Removes not harmful MobEffects of the target, if the Magnitude is Strong Enough
 * <br> Duration (in s) * Amplifier <= Spell-Magnitude * {@link CommonConfig#DISPELLING_SMITE_MAGNITUDE_MOD Factor}
 * <br> -> If the equation is True, the effect can be removed
 * @author Joh0210
 */
public class DispellingSmite extends SmiteMobEffect {
    public DispellingSmite() {
        super(0x4b0082);
    }

    @Override
    public void performSmite(Player source, LivingEntity target, int range, int magnitude, int duration, int precision) {
        ArrayList<MobEffect> effectsToRemove = new ArrayList<>();

        for(MobEffectInstance effectInstance : target.getActiveEffects()){
            if(effectInstance.getDuration() < 6000
                    && effectInstance.getDuration() > 0
                    && effectInstance.getEffect().getCategory() != MobEffectCategory.HARMFUL
                    && effectInstance.getEffect().getCurativeItems().stream().anyMatch(s -> s.getItem() == Items.MILK_BUCKET)
                    && effectInstance.getDuration() / 20.0f * (effectInstance.getAmplifier() + 1) <= magnitude * CommonConfig.DISPELLING_SMITE_MAGNITUDE_MOD.get()
            ){
                effectsToRemove.add(effectInstance.getEffect());
            }
        }

        for (MobEffect effect : effectsToRemove){
            target.removeEffect(effect);
        }
    }
}
