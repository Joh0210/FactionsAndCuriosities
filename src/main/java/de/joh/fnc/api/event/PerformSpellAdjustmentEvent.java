package de.joh.fnc.api.event;

import com.mna.api.events.SpellCastEvent;
import com.mna.api.spells.SpellPartTags;
import de.joh.fnc.api.spelladjustment.SpellAdjustment;
import de.joh.fnc.api.util.Quality;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import org.jetbrains.annotations.NotNull;

@Cancelable
public class PerformSpellAdjustmentEvent extends PlayerEvent {
    public final SpellAdjustment spellAdjustment;

    public final SpellCastEvent spellCastEvent;

    public final SpellPartTags componentTag;

    public PerformSpellAdjustmentEvent(@NotNull SpellAdjustment spellAdjustment, @NotNull SpellCastEvent spellCastEvent, @NotNull SpellPartTags componentTag) {
        super(spellCastEvent.getCaster());
        this.spellAdjustment = spellAdjustment;
        this.spellCastEvent = spellCastEvent;
        this.componentTag = componentTag;
    }

    public Player getSource() {
        return super.getPlayer();
    }

    public Quality getQuality(){
        return spellAdjustment.getQuality(componentTag);
    }
}
