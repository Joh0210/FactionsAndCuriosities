package de.joh.fnc.common.spelladjustment;

import com.mna.api.events.SpellCastEvent;
import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.attributes.Attribute;
import com.mna.api.spells.base.IModifiedSpellPart;
import com.mna.api.spells.parts.Shape;
import de.joh.fnc.api.spelladjustment.SpellAdjustment;
import de.joh.fnc.api.util.Quality;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Doubles the duration of a spell
 * @author Joh0210
 */
public class RenewalSpellAdjustment extends SpellAdjustment {
    /**
     * @param registryName ID under which the upgrade can be recognized
     * @param frequency    How often does the entry appear in the random-selection-list?
     */
    public RenewalSpellAdjustment(@NotNull ResourceLocation registryName, int frequency) {
        super(registryName, frequency);
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        return Quality.GOOD;
    }

    @Override
    protected void performSpellAdjustment(@NotNull SpellCastEvent spellCastEvent) {
        IModifiedSpellPart<Shape> shape =  spellCastEvent.getSpell().getShape();
        shape.getContainedAttributes().stream()
                .filter(attribute -> attribute == Attribute.DURATION)
                .forEach(attribute -> shape.setValue(attribute, shape.getValue(attribute) * 2));

        spellCastEvent.getSpell().getComponents().forEach(modifiedSpellPart -> modifiedSpellPart.getContainedAttributes().stream()
                .filter(attribute -> attribute == Attribute.DURATION)
                .forEach(attribute -> modifiedSpellPart.setValue(attribute, modifiedSpellPart.getValue(attribute) * 2)));
    }

    @Override
    public boolean canBePerformed(@NotNull SpellCastEvent spellCastEvent) {
        AtomicBoolean isDuration = new AtomicBoolean(false);


        spellCastEvent.getSpell().getShape().getContainedAttributes().forEach(attribute -> {
            if(attribute == Attribute.DURATION){
                isDuration.set(true);
            }
        });


        spellCastEvent.getSpell().getComponents().forEach(modifiedSpellPart -> {
            modifiedSpellPart.getContainedAttributes().forEach(attribute -> {
                if (attribute == Attribute.DURATION) {
                    isDuration.set(true);
                }
            });
        });

        return isDuration.get();
    }
}
