package de.joh.fnc.api.smite;

import de.joh.fnc.api.spell.component.SmiteComponent;
import de.joh.fnc.common.event.DamageEventHandler;
import de.joh.fnc.common.util.CommonConfig;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

/**
 * A mob effect given to a Player which applies an {@link SmiteMobEffect#performSmite(Player, LivingEntity, int, int, int, int) effect} when the player makes a direct melee attack against the target.
 * <br>The Duration of the SmiteMobEffect is defined by the {@link CommonConfig}; (Base 30s)
 * @see SmiteHelper
 * @see DamageEventHandler
 * @see SmiteComponent
 * @author Joh0210
 */
public abstract class SmiteMobEffect extends MobEffect {
    public SmiteMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public SmiteMobEffect(int color) {
        super(MobEffectCategory.BENEFICIAL, color);
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity living, @NotNull AttributeMap attributeMap, int amplifier) {
        if(living instanceof Player){
            SmiteHelper.removeSmite((Player) living, this);
        }
        super.removeAttributeModifiers(living, attributeMap, amplifier);
    }

    /**
     * Effect applied to the Smites target
     * @param source Source of the Smite (Attacker)
     * @param target Target of the Smite (Attacked Creature)
     */
    public abstract void performSmite(Player source, LivingEntity target, int range, int magnitude, int duration, int precision);
}
