package de.joh.fnc.common.spelladjustment;

import com.mna.api.events.SpellCastEvent;
import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.attributes.Attribute;
import de.joh.fnc.api.spelladjustment.SpellAdjustment;
import de.joh.fnc.api.util.Quality;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * Increases the strength of all attributes (except Delay) (even over the limit)
 * @author Joh0210
 */
public class EmpoweredSpellAdjustment extends SpellAdjustment {
    /**
     * Strength of the attribute-increase
     */
    private final int strength;

    /**
     * @param registryName ID under which the upgrade can be recognized
     * @param frequency    How often does the entry appear in the random-selection-list?
     * @param strength     Strength of the attribute-increase
     */
    public EmpoweredSpellAdjustment(@NotNull ResourceLocation registryName, int frequency, int strength) {
        super(registryName, frequency);
        this.strength = strength;
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        return strength >= 2 ? Quality.VERY_GOOD : Quality.GOOD;
    }

    @Override
    protected void performSpellAdjustment(@NotNull SpellCastEvent spellCastEvent) {
        spellCastEvent.getSpell().getComponents().forEach(modifiedSpellPart -> modifiedSpellPart.getContainedAttributes().stream()
                .filter(attribute -> attribute != Attribute.DELAY && attribute != Attribute.PRECISION)
                .forEach(attribute -> modifiedSpellPart.setValue(attribute, modifiedSpellPart.getValue(attribute) + strength * modifiedSpellPart.getStep(attribute))));

        spellCastEvent.getSpell().getComponents().forEach(modifiedSpellPart -> modifiedSpellPart.getContainedAttributes().stream()
                .filter(attribute -> attribute == Attribute.PRECISION)
                .forEach(attribute -> modifiedSpellPart.setValue(attribute, Math.min(modifiedSpellPart.getValue(attribute) + strength * modifiedSpellPart.getStep(attribute), modifiedSpellPart.getMaximumValue(attribute)))));
    }
}
