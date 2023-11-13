package de.joh.fnc.common.spelladjustment;

import com.mna.api.events.SpellCastEvent;
import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.attributes.Attribute;
import de.joh.fnc.api.spelladjustment.SpellAdjustment;
import de.joh.fnc.api.util.Quality;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Delays the Spell for 3s
 */
public class DelaySpellAdjustment extends SpellAdjustment {
    /**
     * @param registryName ID under which the upgrade can be recognized
     * @param frequency    How often does the entry appear in the random-selection-list?
     */
    public DelaySpellAdjustment(@NotNull ResourceLocation registryName, int frequency) {
        super(registryName, frequency);
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        return Quality.NEUTRAL;
    }

    @Override
    protected void performSpellAdjustment(@NotNull SpellCastEvent spellCastEvent) {
        Player caster = spellCastEvent.getCaster();
        caster.level.playSound(null, caster.getX(), caster.getY(), caster.getZ(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 0.9F + (float)Math.random() * 0.2F);
        spellCastEvent.getSpell().getComponents().forEach(modifiedSpellPart -> modifiedSpellPart.getContainedAttributes().stream()
                .filter(attribute -> attribute == Attribute.DELAY)
                .forEach(attribute -> modifiedSpellPart.setValue(attribute, modifiedSpellPart.getMaximumValue(attribute))));
    }
}
