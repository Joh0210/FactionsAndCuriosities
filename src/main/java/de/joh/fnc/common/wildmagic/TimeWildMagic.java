package de.joh.fnc.common.wildmagic;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import com.mna.entities.EntityInit;
import com.mna.entities.rituals.TimeChangeBall;
import de.joh.fnc.api.util.Quality;
import de.joh.fnc.api.wildmagic.WildMagic;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Changes the Weather of the casters Dimension
 * @author Joh0210
 */
public class TimeWildMagic extends WildMagic {
    private final boolean day;

    /**
     * @param registryName ID under which the upgrade can be recognized
     * @param frequency    How often does the entry appear in the random-selection-list?
     * @param day         Will it be day (true) or night (false)?
     */
    public TimeWildMagic(@NotNull ResourceLocation registryName, int frequency, boolean day) {
        super(registryName, frequency);
        this.day = day;
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        return Quality.NEUTRAL;
    }

    @Override
    protected void performWildMagic(@NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull SpellPartTags spellPartTag) {
        Entity auroraBall = EntityInit.STARBALL_ENTITY.get().spawn((ServerLevel)source.getLevel(), null, null, source.blockPosition().above(2), MobSpawnType.TRIGGERED, true, false);
        if (auroraBall instanceof TimeChangeBall) {
            ((TimeChangeBall) auroraBall).setTimeChangeType(this.day ? TimeChangeBall.TIME_CHANGE_DAY : TimeChangeBall.TIME_CHANGE_NIGHT);
        }
    }

    @Override
    public boolean canBePerformed(@NotNull LivingEntity source, @Nullable SpellTarget target) {
        if(source.getLevel().dimensionType().hasFixedTime()){
            return false;
        }

        return this.day != source.getLevel().isDay();
    }
}
