package de.joh.fnc.common.effect.harmful;

import de.joh.fnc.common.event.CommonEventHandler;
import de.joh.fnc.common.event.DamageEventHandler;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

/**
 * Stops the Entity form {@link CommonEventHandler#onLivingHeal(LivingHealEvent) Healing} and
 * {@link DamageEventHandler#onLivingHurt(LivingHurtEvent event) damage taken} will be increased by 25% per level
 * @see CommonEventHandler
 * @see DamageEventHandler
 * @author Joh0210
 */
public class HexMobEffect extends MobEffect {
    public HexMobEffect() {
        super(MobEffectCategory.HARMFUL, 0x000000);
    }
}
