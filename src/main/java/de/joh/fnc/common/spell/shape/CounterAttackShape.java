package de.joh.fnc.common.spell.shape;

import com.mna.api.entities.ISpellInteractibleEntity;
import com.mna.api.faction.IFaction;
import com.mna.api.spells.ComponentApplicationResult;
import com.mna.api.spells.attributes.Attribute;
import com.mna.api.spells.attributes.AttributeValuePair;
import com.mna.api.spells.base.IModifiedSpellPart;
import com.mna.api.spells.base.ISpellDefinition;
import com.mna.api.spells.parts.Shape;
import com.mna.api.spells.parts.SpellEffect;
import com.mna.api.spells.targeting.SpellContext;
import com.mna.api.spells.targeting.SpellSource;
import com.mna.api.spells.targeting.SpellTarget;
import com.mna.spells.SpellCaster;
import de.joh.fnc.api.event.AddSmiteEvent;
import de.joh.fnc.common.capability.PlayerCapabilityProvider;
import de.joh.fnc.common.init.EffectInit;
import de.joh.fnc.common.init.FactionInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Allows you to counterattack when attacked. While the effect is active, a charge is used to cast the stored spell on attackers.
 * @author Joh0210
 */
public class CounterAttackShape extends Shape {
    public CounterAttackShape(ResourceLocation guiIcon) {
        super(guiIcon, new AttributeValuePair(Attribute.DURATION, 30F, 15F, 300F, 30F, 10F), new AttributeValuePair(Attribute.MAGNITUDE, 1F, 1F, 5F, 1F, 30F));
    }

    public List<SpellTarget> Target(SpellSource source, Level world, IModifiedSpellPart<Shape> modificationData, ISpellDefinition recipe) {
        if (source.getPlayer() != null) {
            AddSmiteEvent event = new AddSmiteEvent(source.getPlayer());
            MinecraftForge.EVENT_BUS.post(event);
            if (!event.isCanceled()) {
                source.getPlayer().addEffect(new MobEffectInstance(EffectInit.COUNTER_ATTACK.get(), (int) modificationData.getValue(Attribute.DURATION) * 20, (int) modificationData.getValue(Attribute.MAGNITUDE)-1));
                source.getPlayer().getCapability(PlayerCapabilityProvider.PLAYER_SMITE).ifPresent(smiteCapability -> smiteCapability.addCounterAttackFromShape(recipe));

                return List.of(new SpellTarget(source.getCaster()));
            }

        }
        return Collections.singletonList(SpellTarget.NONE);
    }

    /**
     * No entity will be spawned, but the Effect will not be applied immediately
     */
    public boolean spawnsTargetEntity() {
        return true;
    }

    @Override
    public IFaction getFactionRequirement() {
        return FactionInit.PALADIN;
    }
    @Override
    public boolean canBeOnRandomStaff() {
        return false;
    }

    public float initialComplexity() {
        return 20.0F;
    }

    public int requiredXPForRote() {
        return 300;
    }

    public static void applyCounterAttack(@NotNull Player source, @Nullable LivingEntity attacker){
        MobEffectInstance effect = source.getEffect(EffectInit.COUNTER_ATTACK.get());
        if(effect != null && attacker != null) {
            AtomicReference<ISpellDefinition> counterAttack = new AtomicReference<>();
            source.getCapability(PlayerCapabilityProvider.PLAYER_SMITE).ifPresent(smiteCapability -> {
                counterAttack.set(smiteCapability.getCounterAttackFromShape());
            });

            if(counterAttack.get() != null && counterAttack.get().isValid() && !source.level().isClientSide) {
                SpellSource spellSource = new SpellSource(source, InteractionHand.MAIN_HAND);
                HashMap<SpellEffect, ComponentApplicationResult> results = new HashMap<>();

                if (attacker instanceof ISpellInteractibleEntity && ((ISpellInteractibleEntity<?>) attacker).onShapeTarget(counterAttack.get(), spellSource)) {
                    return;
                }

                SpellContext context = new SpellContext(source.level(), counterAttack.get());
                if (counterAttack.get().getComponents().stream().anyMatch((c) -> c.getPart().targetsEntities())) {
                    HashMap<SpellEffect, ComponentApplicationResult> loopRes = SpellCaster.ApplyComponents(counterAttack.get(), spellSource, new SpellTarget(attacker), context);
                    SpellCaster.mergeComponentResults(results, loopRes);
                }

                if (!results.isEmpty()) {
                    List<SpellEffect> appliedEffects = results.entrySet().stream()
                            .map(e -> e.getValue() == ComponentApplicationResult.SUCCESS ? e.getKey() : null)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    SpellCaster.spawnClientFX(source.level(), attacker.position(), source.getLookAngle(), spellSource, appliedEffects);
                }

                int amplifier = effect.getAmplifier();
                int duration = effect.getDuration();

                source.removeEffect(EffectInit.COUNTER_ATTACK.get());
                if(amplifier > 0) {
                    source.addEffect(new MobEffectInstance(EffectInit.COUNTER_ATTACK.get(), duration, amplifier-1));
                    source.getCapability(PlayerCapabilityProvider.PLAYER_SMITE).ifPresent(smiteCapability -> smiteCapability.addCounterAttackFromShape(counterAttack.get()));
                }
            }
        }
    }
}
