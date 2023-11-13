package de.joh.fnc.common.spelladjustment;

import com.mna.api.events.SpellCastEvent;
import com.mna.api.spells.SpellPartTags;
import de.joh.fnc.api.spelladjustment.SpellAdjustment;
import de.joh.fnc.api.util.Quality;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * Cancels the casted Spell
 * @author Joh0210
 */
public class CancelSpellAdjustment extends SpellAdjustment {
    /**
     * @param registryName ID under which the upgrade can be recognized
     * @param frequency    How often does the entry appear in the random-selection-list?
     */
    public CancelSpellAdjustment(@NotNull ResourceLocation registryName, int frequency) {
        super(registryName, frequency);
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        return Quality.VERY_BAD;
    }

    @Override
    protected void performSpellAdjustment(@NotNull SpellCastEvent spellCastEvent) {
        spellCastEvent.setCanceled(true);
    }
}
