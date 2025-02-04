package de.joh.fnc.common.spelladjustment;

import com.mna.api.events.SpellCastEvent;
import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellContext;
import com.mna.api.spells.targeting.SpellSource;
import com.mna.api.spells.targeting.SpellTarget;
import com.mna.api.timing.DelayedEventQueue;
import com.mna.api.timing.TimedDelayedSpellEffect;
import com.mna.spells.shapes.ShapeSelf;
import de.joh.fnc.api.spelladjustment.SpellAdjustment;
import de.joh.fnc.api.util.Quality;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Applies the Spell also on the Caster
 * @author Joh0210
 */
public class ShareSpellAdjustment extends SpellAdjustment {
    /**
     * @param registryName ID under which the upgrade can be recognized
     * @param frequency    How often does the entry appear in the random-selection-list?
     */
    public ShareSpellAdjustment(@NotNull ResourceLocation registryName, int frequency) {
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
        if(event.getSource().getCaster() instanceof Player caster) {
            event.getSpell().getComponents().forEach(comp -> {
                int delay = (int) (comp.getValue(com.mna.api.spells.attributes.Attribute.DELAY) * 20.0F);
                if (delay > 0) {
                    DelayedEventQueue.pushEvent(caster.level(), new TimedDelayedSpellEffect(comp.getPart().getRegistryName().toString(), delay, new SpellSource(caster, InteractionHand.MAIN_HAND), new SpellTarget(caster), comp, new SpellContext(caster.level(), event.getSpell())));
                } else {
                    comp.getPart().ApplyEffect(new SpellSource(caster, InteractionHand.MAIN_HAND), new SpellTarget(caster), comp, new SpellContext(caster.level(), event.getSpell()));
                }
            });
        }
    }

    @Override
    public boolean canBePerformed(@NotNull SpellCastEvent spellCastEvent) {
        if(spellCastEvent.getSpell().getShape().getPart() instanceof ShapeSelf){
            return false;
        }

        AtomicBoolean targetsEntities = new AtomicBoolean(false);

        spellCastEvent.getSpell().getComponents().forEach(modifiedSpellPart -> {
            if(modifiedSpellPart.getPart().targetsEntities()){
                targetsEntities.set(true);
            }
        });

        return targetsEntities.get();
    }
}
