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
 * Maximizes the precision of the spell
 * @author Joh0210
 */
public class PreciseSpellAdjustment extends SpellAdjustment {
    /**
     * @param registryName ID under which the upgrade can be recognized
     * @param frequency    How often does the entry appear in the random-selection-list?
     */
    public PreciseSpellAdjustment(@NotNull ResourceLocation registryName, int frequency) {
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
                .filter(attribute -> attribute == Attribute.PRECISION)
                .forEach(attribute -> shape.setValue(attribute, shape.getMaximumValue(attribute)));

        spellCastEvent.getSpell().getComponents().forEach(modifiedSpellPart -> modifiedSpellPart.getContainedAttributes().stream()
                .filter(attribute -> attribute == Attribute.PRECISION)
                .forEach(attribute -> modifiedSpellPart.setValue(attribute, modifiedSpellPart.getMaximumValue(attribute))));
    }

    @Override
    public boolean canBePerformed(@NotNull SpellCastEvent spellCastEvent) {
        AtomicBoolean isPrecision = new AtomicBoolean(false);


        spellCastEvent.getSpell().getShape().getContainedAttributes().forEach(attribute -> {
            if(attribute == Attribute.PRECISION){
                isPrecision.set(true);
            }
        });


        spellCastEvent.getSpell().getComponents().forEach(modifiedSpellPart -> {
            modifiedSpellPart.getContainedAttributes().forEach(attribute -> {
                if (attribute == Attribute.PRECISION) {
                    isPrecision.set(true);
                }
            });
        });

        return isPrecision.get();
    }
}
