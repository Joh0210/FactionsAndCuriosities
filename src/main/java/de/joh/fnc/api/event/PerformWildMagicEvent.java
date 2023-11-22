package de.joh.fnc.api.event;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.api.util.Quality;
import de.joh.fnc.api.wildmagic.WildMagic;
import de.joh.fnc.api.wildmagic.WildMagicHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This event is fired when Wild Magic will occur ({@link ShouldCauseWildMagicEvent#shouldCauseWildMagic()} == ture).
 * <br>If the event is canceled, the Wild Magic will not come into effect.
 * <br>You can also set the event to ignore the canceling.
 * <br><br>This event is fired on the {@link MinecraftForge#EVENT_BUS}.
 * @see WildMagicHelper
 * @see ShouldCauseWildMagicEvent
 * @author Joh0210
 */
@Cancelable
public class PerformWildMagicEvent extends LivingEvent {
    public final WildMagic wildMagic;

    @Nullable
    public final SpellTarget target;

    public final SpellPartTags componentTag;

    /**
     * If ture, the event can not be canceled.
     */
    private final boolean cancelable;

    /**
     * @param source Source of the Wild Magic
     * @param target Target of the Spell
     * @param wildMagic Wild Magic that will take place
     * @param componentTag Tag of the first SpellEffect of the Spell
     * @param cancelable If ture, the event can not be canceled
     */
    public PerformWildMagicEvent(@NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull WildMagic wildMagic, @NotNull SpellPartTags componentTag, boolean cancelable) {
        super(source);
        this.wildMagic = wildMagic;
        this.target = target;
        this.componentTag = componentTag;
        this.cancelable = cancelable;
    }

    public LivingEntity getSource() {
        return super.getEntityLiving();
    }

    public Quality getQuality(){
        return wildMagic.getQuality(componentTag);
    }

    @Override
    public boolean isCancelable() {
        return cancelable;
    }
}
