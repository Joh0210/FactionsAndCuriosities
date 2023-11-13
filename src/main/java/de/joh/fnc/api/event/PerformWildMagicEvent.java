package de.joh.fnc.api.event;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.api.util.Quality;
import de.joh.fnc.api.wildmagic.WildMagic;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Cancelable
public class PerformWildMagicEvent extends LivingEvent {
    public final WildMagic wildMagic;

    @Nullable
    public final SpellTarget target;

    public final SpellPartTags componentTag;
    private final boolean cancelable;

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
