package de.joh.fnc.common.effect.harmful;

import de.joh.fnc.api.util.BloodMagicHelper;
import de.joh.fnc.common.capability.GamblerBlowCapabilityProvider;
import de.joh.fnc.common.init.EffectInit;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import org.jetbrains.annotations.NotNull;

/**
 * If the opponent takes damage, it is used as a sacrifice for the blood mage who casted the original spell.
 * @author Joh0210
 */
public class SacrificialLambMobEffect extends MobEffect {
    public SacrificialLambMobEffect() {
        super(MobEffectCategory.HARMFUL, 0x440000);
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity livingEntity, @NotNull AttributeMap attributeMap, int amplifier) {
        livingEntity.getCapability(GamblerBlowCapabilityProvider.GAMBLER_BLOW).ifPresent(cap -> cap.setSacrificialLambSource(null));
        super.removeAttributeModifiers(livingEntity, attributeMap, amplifier);
    }

    public static void sacrificeLamb(LivingDamageEvent event) {
        if (!event.getEntity().level().isClientSide() && event.getEntity().hasEffect(EffectInit.SACRIFICIAL_LAMB.get())) {
            event.getEntity().getCapability(GamblerBlowCapabilityProvider.GAMBLER_BLOW).ifPresent(cap -> {
                if (cap.getSacrificialLambSource() != null) {
                    if(cap.getSacrificialLambSource() != event.getEntity().getUUID()) {
                        Player bloodMage = event.getEntity().level().getPlayerByUUID(cap.getSacrificialLambSource());
                        if (bloodMage != null) {
                            BloodMagicHelper.regenerateMana(bloodMage, event.getEntity(), event.getAmount());
                        }
                    }
                }
            });
        }
    }
}
