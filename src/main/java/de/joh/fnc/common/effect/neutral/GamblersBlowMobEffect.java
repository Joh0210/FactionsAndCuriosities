package de.joh.fnc.common.effect.neutral;

import de.joh.fnc.api.wildmagic.WildMagicHelper;
import de.joh.fnc.common.capability.GamblerBlowCapability;
import de.joh.fnc.common.capability.GamblerBlowCapabilityProvider;
import de.joh.fnc.common.init.EffectInit;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class GamblersBlowMobEffect extends MobEffect {
    public GamblersBlowMobEffect() {
        super(MobEffectCategory.NEUTRAL, 0x009000);
    }

    public static boolean eventHandleDamageAdjustment(LivingHurtEvent event){
        if(event.isCanceled()) return true;

        AtomicReference<Float> amount = new AtomicReference<>(event.getAmount());

        LivingEntity defender = event.getEntity();
        if(defender.hasEffect(EffectInit.GAMBLERS_BLOW_DEFENSIVE.get())){
            MobEffectInstance gamblersBlow = defender.getEffect(EffectInit.GAMBLERS_BLOW_DEFENSIVE.get());
            int precision = defender.getCapability(GamblerBlowCapabilityProvider.GAMBLER_BLOW).map(GamblerBlowCapability::getDefensivePrecision).orElse(1);
            amount.set(generateModifier(defender, gamblersBlow.getAmplifier(), precision, false) * amount.get());
        }

        if(event.getSource().getEntity() instanceof LivingEntity attacker && attacker.hasEffect(EffectInit.GAMBLERS_BLOW_OFFENSIVE.get())){
            MobEffectInstance gamblersBlow = attacker.getEffect(EffectInit.GAMBLERS_BLOW_OFFENSIVE.get());
            int precision = attacker.getCapability(GamblerBlowCapabilityProvider.GAMBLER_BLOW).map(GamblerBlowCapability::getOffensivePrecision).orElse(1);
            amount.set(generateModifier(attacker, gamblersBlow.getAmplifier(), precision, true) * amount.get());
        }

        event.setAmount(amount.get());
        return false;
    }

    public static float generateModifier(LivingEntity user, int level, int precision, boolean higherBetter) {
        int luck = (user != null) ? WildMagicHelper.getWildMagicLuck(user) : 0;
        int rolls = Math.abs(luck) + 1;

        boolean isLucky = luck >= 0;
        boolean chooseHigher = isLucky == higherBetter;

        float bestModifier = generateRandomFloat(level, precision);

        for (int i = 1; i < rolls; i++) {
            float currentRoll = generateRandomFloat(level, precision);

            if (chooseHigher) {
                bestModifier = Math.max(bestModifier, currentRoll);
            } else {
                bestModifier = Math.min(bestModifier, currentRoll);
            }
        }

        return bestModifier;
    }

    public static float generateRandomFloat(int level, int precision) {
        float max = 1.33f + (level * 0.33f);

        Random random = new Random();
        precision = Math.max(1, precision);
        float ret = 0.0f;

        for(int i = 0; i < precision; i++) {
            float n = 1.0f + random.nextFloat() * (max - 1.0f);
            if (random.nextBoolean()) {
                n = 1.0f / n;
            }
            ret += n;
        }

        return ret / precision;
    }
}
