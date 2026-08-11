package de.joh.fnc.common.effect.harmful;

import de.joh.fnc.common.capability.GamblerBlowCapabilityProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import de.joh.fnc.common.util.RLoc;

import java.util.ArrayList;
import java.util.List;

public class FinalDualMobEffect extends MobEffect {
    public FinalDualMobEffect()
    {
        super(MobEffectCategory.HARMFUL, 0x880000 );
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        if(livingEntity.level().isClientSide) return;

        livingEntity.getCapability(GamblerBlowCapabilityProvider.GAMBLER_BLOW).ifPresent(cap -> {
            if (cap.getFinalDualEnemie() == null) {
                livingEntity.removeEffect(this);
                return;
            }

            Player enemy = livingEntity.level().getPlayerByUUID(cap.getFinalDualEnemie());
            if (enemy == null) {
                livingEntity.removeEffect(this);
                return;
            }

            enemy.getCapability(GamblerBlowCapabilityProvider.GAMBLER_BLOW).ifPresent(enemyCap -> {
                if (enemyCap.isFinalDualLost()) {
                    livingEntity.removeEffect(this);
                } else {
                    float dmg = (amplifier * 2.0f) / cap.getFinalDualPrecision();
                    String damageType = switch (cap.getFinalDualPrecision()) {
                        case 2 -> "final_dual_bypass_armor";
                        case 3 -> "final_dual_bypass_all";
                        default -> "final_dual";
                    };

                    DamageSource damageSource = new DamageSource(
                            livingEntity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(
                                    ResourceKey.create(Registries.DAMAGE_TYPE, RLoc.create(damageType))
                            ),
                            enemy,
                            enemy
                    );
                    livingEntity.hurt(damageSource, dmg);
                    enemy.hurt(damageSource, dmg);
                }
            });
        });
    }


    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        int i = 20;
        return duration % i == 0;
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity livingEntity, @NotNull AttributeMap attributeMap, int amplifier) {
        livingEntity.getCapability(GamblerBlowCapabilityProvider.GAMBLER_BLOW).ifPresent(cap -> cap.setFinalDualEnemie(null));
        super.removeAttributeModifiers(livingEntity, attributeMap, amplifier);
    }
}
