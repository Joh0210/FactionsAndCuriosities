package de.joh.fnc.spelladjustment.init;

import com.mna.api.events.SpellCastEvent;
import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellContext;
import com.mna.api.spells.targeting.SpellSource;
import com.mna.api.spells.targeting.SpellTarget;
import com.mna.api.timing.DelayedEventQueue;
import com.mna.api.timing.TimedDelayedSpellEffect;
import de.joh.fnc.spelladjustment.util.SpellAdjustment;
import de.joh.fnc.wildmagic.util.Quality;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Applies the Spell also on the Caster
 * @author Joh0210
 *
 */
public class SpellAdjustmentShare extends SpellAdjustment {
    /**
     * @param registryName ID under which the upgrade can be recognized
     * @param frequency    How often does the entry appear in the random-selection-list?
     */
    public SpellAdjustmentShare(@NotNull ResourceLocation registryName, int frequency) {
        super(registryName, frequency);
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        return switch (componentTag) {
            case HARMFUL -> Quality.VERY_BAD;
            case FRIENDLY -> Quality.GOOD;
            default -> Quality.NEUTRAL;
        };
    }

    @Override
    protected void performSpellAdjustment(@NotNull SpellCastEvent event) {
        Player caster = event.getCaster();

        event.getSpell().getComponents().forEach(comp -> {
            int delay = (int)(comp.getValue(com.mna.api.spells.attributes.Attribute.DELAY) * 20.0F);
            if (delay > 0) {
                DelayedEventQueue.pushEvent(caster.level, new TimedDelayedSpellEffect(comp.getPart().getRegistryName().toString(), delay, new SpellSource(caster, InteractionHand.MAIN_HAND), new SpellTarget(caster), comp, new SpellContext(caster.getLevel(), event.getSpell())));
            } else {
                comp.getPart().ApplyEffect(new SpellSource(caster, InteractionHand.MAIN_HAND), new SpellTarget(caster), comp, new SpellContext(caster.getLevel(), event.getSpell()));
            }
        });
    }
}
