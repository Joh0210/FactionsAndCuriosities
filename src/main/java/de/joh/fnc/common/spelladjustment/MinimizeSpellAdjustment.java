package de.joh.fnc.common.spelladjustment;

import com.mna.api.events.SpellCastEvent;
import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.attributes.Attribute;
import de.joh.fnc.api.spelladjustment.SpellAdjustment;
import de.joh.fnc.api.util.Quality;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * Minimizes all Modifiers of a spell (except Delay)
 * @author Joh0210
 */
public class MinimizeSpellAdjustment extends SpellAdjustment {
    /**
     * @param registryName ID under which the upgrade can be recognized
     * @param frequency    How often does the entry appear in the random-selection-list?
     */
    public MinimizeSpellAdjustment(@NotNull ResourceLocation registryName, int frequency) {
        super(registryName, frequency);
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        return Quality.VERY_BAD;
    }

    @Override
    protected void performSpellAdjustment(@NotNull SpellCastEvent spellCastEvent) {
        spellCastEvent.getSpell().getComponents().forEach(modifiedSpellPart -> modifiedSpellPart.getContainedAttributes().stream()
                .filter(attribute -> attribute != Attribute.DELAY)
                .forEach(attribute -> modifiedSpellPart.setValue(attribute, modifiedSpellPart.getMinimumValue(attribute))));
    }
}
