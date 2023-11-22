package de.joh.fnc.api.smite;

import de.joh.fnc.api.event.PerformSmiteEvent;
import de.joh.fnc.api.spell.component.SmiteComponent;
import de.joh.fnc.common.capability.SmiteEntry;
import de.joh.fnc.common.capability.SmitePlayerCapability;
import de.joh.fnc.common.capability.PlayerCapabilityProvider;
import de.joh.fnc.common.event.DamageEventHandler;
import de.joh.fnc.common.util.CommonConfig;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A Helper for Smites.
 * @see SmiteMobEffect
 * @see SmiteComponent
 * @author Joh0210
 */
public class SmiteHelper {
    /**
     * Adds the Smite Effect to a Player as a Mob Effect and NBT Data.
     * <br>The Duration of the SmiteMobEffect is defined by the {@link CommonConfig}; (Base 30s)
     * @see SmiteComponent
     * @see SmitePlayerCapability
     */
    public static void addSmite(Player player, SmiteMobEffect smiteEffect, int damage, int range, int magnitude, int duration, int precision) {
        player.getCapability(PlayerCapabilityProvider.PLAYER_SMITE).ifPresent(smiteCapability -> smiteCapability.addSmite(smiteEffect, damage, range, magnitude, duration, precision));

        if(!player.hasEffect(smiteEffect)){
            player.addEffect(new MobEffectInstance(smiteEffect, CommonConfig.SMITE_DURATION.get() * 20, 0));
        }
        else{
            //Update the duration of the effect
            player.getEffect(smiteEffect).update(new MobEffectInstance(smiteEffect, CommonConfig.SMITE_DURATION.get() * 20, 0));
        }
    }

    /**
     * Removes a Smite Effect from a Players NBT Data.
     * <br>Called when the {@link SmiteMobEffect#removeAttributeModifiers(LivingEntity, AttributeMap, int) SmiteMobEffect} runes out.
     * @see SmiteMobEffect
     * @see SmitePlayerCapability
     */
    public static void removeSmite(Player player, SmiteMobEffect smiteEffect){
        player.getCapability(PlayerCapabilityProvider.PLAYER_SMITE).ifPresent(smiteCapability -> smiteCapability.removeSmite(smiteEffect));
    }

    /**
     * Applies the Smite Effect on the Target.
     * <br>Called when the source makes a direct melee attack against the target.
     * <br><br>In addition to the actual {@link SmiteMobEffect#performSmite(Player, LivingEntity, int, int, int, int) Smite-Effects}, a certain amount of damage is also added.
     * The base value cannot exceed the amount defined by the {@link CommonConfig}, but the {@link PerformSmiteEvent#damageMod} is not effected by this rule.
     * @see DamageEventHandler
     */
    public static void applySmite(Player source, LivingEntity target){
        AtomicReference<ArrayList<SmiteEntry>> smites = new AtomicReference<>(new ArrayList<>());
        source.getCapability(PlayerCapabilityProvider.PLAYER_SMITE).ifPresent(smiteCapability -> smites.set(smiteCapability.getSmites()));

        if(smites.get().isEmpty()){
            return;
        }

        PerformSmiteEvent event = new PerformSmiteEvent(source, target, smites.get());
        MinecraftForge.EVENT_BUS.post(event);
        if(!event.isCanceled()) {
            for(SmiteEntry entry : event.getSmites()){
                entry.getSmite().performSmite(source, target, entry.getRange(), entry.getMagnitude(), entry.getDuration(), entry.getPrecision());
            }

            target.hurt(new EntityDamageSource("fnc-smite", source).bypassArmor().bypassMagic().setMagic(), event.getDamage());
        }
    }
}
