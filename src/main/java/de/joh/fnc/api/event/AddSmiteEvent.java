package de.joh.fnc.api.event;

import de.joh.fnc.api.smite.SmiteHelper;
import de.joh.fnc.common.spell.shape.PaladinSmiteShape;
import de.joh.fnc.common.util.CommonConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import org.jetbrains.annotations.NotNull;

/**
 * This event is fired when a Smite will is Added to a Player.
 * <br>If the event is canceled, the Smite will not come into effect.
 * <br><br>This event is fired on the {@link MinecraftForge#EVENT_BUS}.
 * @see SmiteHelper
 * @see PaladinSmiteShape
 * @author Joh0210
 */
@Cancelable
public class AddSmiteEvent extends PlayerEvent {
    /**
     * Duration of the Smite in ticks
     */
    private int duration;

    public AddSmiteEvent(@NotNull Player source) {
        super(source);
        this.duration = CommonConfig.SMITE_DURATION.get() * 20;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void addDuration(int duration) {
        this.duration += duration;
    }
}
