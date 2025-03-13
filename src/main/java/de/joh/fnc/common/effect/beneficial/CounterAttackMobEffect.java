package de.joh.fnc.common.effect.beneficial;

import de.joh.fnc.common.capability.PlayerCapabilityProvider;
import de.joh.fnc.common.capability.SmitePlayerCapability;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Allows you to counterattack when attacked. While the effect is active, a charge is used to cast the stored spell on attackers.
 * @author Joh0210
 */
public class CounterAttackMobEffect extends MobEffect {
    public CounterAttackMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xc0c0c0);
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity living, @NotNull AttributeMap attributeMap, int amplifier) {
        if(living instanceof Player){
            living.getCapability(PlayerCapabilityProvider.PLAYER_SMITE).ifPresent(SmitePlayerCapability::removeCounterAttackFromShape);
        }
        super.removeAttributeModifiers(living, attributeMap, amplifier);
    }
}
