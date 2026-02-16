package de.joh.fnc.common.effect.beneficial;

import de.joh.fnc.common.init.EffectInit;
import de.joh.fnc.common.util.CommonConfig;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class FrenzyMobEffect extends MobEffect {
    public FrenzyMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x5e2129);
    }

    public static void eventFrenzyDamage(LivingHurtEvent event){
        if(event.getSource().getEntity() instanceof LivingEntity attacker && !attacker.level().isClientSide()) {
            MobEffectInstance frenzyEffect = attacker.getEffect(EffectInit.FRENZY.get());

            if (frenzyEffect != null) {
                int level = frenzyEffect.getAmplifier() + 1;
                if(level > 0) {
                    event.setAmount(event.getAmount() * (1+level*CommonConfig.FRENZY_BOOST.get()*0.01f));
                }
            }
        }
    }
}
