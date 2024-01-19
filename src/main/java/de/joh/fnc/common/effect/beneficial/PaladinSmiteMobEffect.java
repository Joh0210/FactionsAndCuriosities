package de.joh.fnc.common.effect.beneficial;

import com.mna.api.entities.ISpellInteractibleEntity;
import com.mna.api.spells.ComponentApplicationResult;
import com.mna.api.spells.base.ISpellDefinition;
import com.mna.api.spells.parts.SpellEffect;
import com.mna.api.spells.targeting.SpellContext;
import com.mna.api.spells.targeting.SpellSource;
import com.mna.api.spells.targeting.SpellTarget;
import com.mna.spells.SpellCaster;
import de.joh.fnc.api.smite.SmiteHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PaladinSmiteMobEffect extends MobEffect {
    public PaladinSmiteMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xc0c0c0);
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity living, @NotNull AttributeMap attributeMap, int amplifier) {
        if(living instanceof Player){
            SmiteHelper.removeSmiteFromShape((Player) living);
        }
        super.removeAttributeModifiers(living, attributeMap, amplifier);
    }

    public static void performSmite(Player source, LivingEntity target, ISpellDefinition smite) {
        if (smite.isValid() && !source.level().isClientSide) {
            SpellSource spellSource = new SpellSource(source, InteractionHand.MAIN_HAND);
            HashMap<SpellEffect, ComponentApplicationResult> results = new HashMap<>();

            if (target instanceof ISpellInteractibleEntity && ((ISpellInteractibleEntity<?>) target).onShapeTarget(smite, spellSource)) {
                return;
            }

            SpellContext context = new SpellContext(source.level(), smite);

            if (smite.getComponents().stream().anyMatch((c) -> c.getPart().targetsEntities())) {
                HashMap<SpellEffect, ComponentApplicationResult> loopRes = SpellCaster.ApplyComponents(smite, spellSource, new SpellTarget(target), context);
                SpellCaster.mergeComponentResults(results, loopRes);
            }

            if (!results.isEmpty()) {
                List<SpellEffect> appliedEffects = results.entrySet().stream()
                        .map(e -> e.getValue() == ComponentApplicationResult.SUCCESS ? e.getKey() : null)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                SpellCaster.spawnClientFX(source.level(), target.position(), source.getLookAngle(), spellSource, appliedEffects);
            }
        }
    }
}
