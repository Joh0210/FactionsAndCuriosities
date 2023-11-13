package de.joh.fnc.common.spelladjustment;

import com.mna.api.events.SpellCastEvent;
import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.attributes.Attribute;
import com.mna.api.spells.base.IDamageComponent;
import de.joh.fnc.api.spelladjustment.SpellAdjustment;
import de.joh.fnc.api.util.Quality;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Increases the damage of a spell and a factor of 5
 * @author Joh0210
 */
public class PowerSpellAdjustment extends SpellAdjustment {
    /**
     * @param registryName ID under which the upgrade can be recognized
     * @param frequency    How often does the entry appear in the random-selection-list?
     */
    public PowerSpellAdjustment(@NotNull ResourceLocation registryName, int frequency) {
        super(registryName, frequency);
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        return Quality.VERY_GOOD;
    }

    @Override
    protected void performSpellAdjustment(@NotNull SpellCastEvent spellCastEvent) {
        spellCastEvent.getSpell().getComponents().forEach(modifiedSpellPart -> modifiedSpellPart.getContainedAttributes().stream()
                .filter(attribute -> attribute == Attribute.DAMAGE)
                .forEach(attribute -> modifiedSpellPart.setValue(attribute, modifiedSpellPart.getValue(attribute) * 5)));
    }

    @Override
    public boolean canBePerformed(@NotNull SpellCastEvent spellCastEvent) {
        AtomicBoolean isDamage = new AtomicBoolean(false);

        spellCastEvent.getSpell().getComponents().forEach(modifiedSpellPart -> {
            if(modifiedSpellPart.getPart() instanceof IDamageComponent){
                isDamage.set(true);
            }
        });

        return isDamage.get();
    }
}
