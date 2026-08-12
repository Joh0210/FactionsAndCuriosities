package de.joh.fnc.common.effect.beneficial;

import de.joh.fnc.common.capability.GamblerBlowCapabilityProvider;
import de.joh.fnc.common.init.EffectInit;
import de.joh.fnc.common.util.RLoc;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Postponement: All damage is ignored while the spell's effect is active.
 * Once the spell ends, the combined damage is immediately inflicted.
 * @author Joh0210
 */
public class PostponementMobEffect extends MobEffect {
    public PostponementMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x00FF88);
    }

    public static boolean postponement(LivingHurtEvent event){
        if (event.getEntity().hasEffect(EffectInit.POSTPONEMENT.get()) && !event.getSource().is(ResourceKey.create(Registries.DAMAGE_TYPE, RLoc.create("postponement_damage")))) {
            event.getEntity().getCapability(GamblerBlowCapabilityProvider.GAMBLER_BLOW).ifPresent(cap -> cap.addPostponedDamage(event.getAmount()));
            event.setCanceled(true);
            return true;
        }
        return false;
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity livingEntity, @NotNull AttributeMap attributeMap, int amplifier) {
        livingEntity.getCapability(GamblerBlowCapabilityProvider.GAMBLER_BLOW).ifPresent(cap -> {
            float damage = cap.getPostponedDamage();
            if (damage > 0) {
                DamageSource damageSource = new DamageSource(
                        livingEntity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(
                                ResourceKey.create(Registries.DAMAGE_TYPE, RLoc.create("postponement_damage"))
                        )
                );
                livingEntity.hurt(damageSource, damage);
                cap.setPostponedDamage(0);
            }
        });
        livingEntity.addEffect(new MobEffectInstance(com.mna.effects.EffectInit.CHRONO_EXHAUSTION.get(), 1200));
        super.removeAttributeModifiers(livingEntity, attributeMap, amplifier);
    }
}
