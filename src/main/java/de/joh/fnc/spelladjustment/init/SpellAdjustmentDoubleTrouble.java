package de.joh.fnc.spelladjustment.init;

import com.mna.api.events.SpellCastEvent;
import com.mna.api.spells.SpellPartTags;
import de.joh.fnc.spelladjustment.util.SpellAdjustment;
import de.joh.fnc.spelladjustment.util.SpellAdjustmentHelper;
import de.joh.fnc.wildmagic.util.Quality;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * Performs two SpellAdjustments
 * @author Joh0210
 */
public class SpellAdjustmentDoubleTrouble extends SpellAdjustment {
    /**
     * @param registryName ID under which the upgrade can be recognized
     * @param frequency    How often does the entry appear in the random-selection-list?
     */
    public SpellAdjustmentDoubleTrouble(@NotNull ResourceLocation registryName, int frequency) {
        super(registryName, frequency);
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        return Quality.NEUTRAL;
    }

    @Override
    protected void performSpellAdjustment(@NotNull SpellCastEvent spellCastEvent) {
        SpellAdjustmentHelper.performRandomSpellAdjustment(spellCastEvent, (adjustment, caster, spell) -> !(adjustment instanceof SpellAdjustmentDoubleTrouble));
        SpellAdjustmentHelper.performRandomSpellAdjustment(spellCastEvent, (adjustment, caster, spell) -> !(adjustment instanceof SpellAdjustmentDoubleTrouble));
    }
}
