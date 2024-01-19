package de.joh.fnc.common.wildmagic;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.api.util.Quality;
import de.joh.fnc.api.wildmagic.WildMagic;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Changes the Weather of the casters Dimension
 * @author Joh0210
 */
public class WeatherWildMagic extends WildMagic {
    private final boolean rain;
    private final boolean storm;

    /**
     * @param registryName ID under which the upgrade can be recognized
     * @param frequency    How often does the entry appear in the random-selection-list?
     * @param rain         Will it be rain (true) or shine (false)?
     * @param storm        If it rains, it should also be a storm?
     */
    public WeatherWildMagic(@NotNull ResourceLocation registryName, int frequency, boolean rain, boolean storm) {
        super(registryName, frequency);
        this.rain = rain;
        this.storm = storm && rain;
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        return Quality.NEUTRAL;
    }

    @Override
    protected void performWildMagic(@NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull SpellPartTags spellPartTag) {
        if(this.rain){
            ((ServerLevel)source.level()).setWeatherParameters(0, 6000, true, this.storm);
        } else {
            ((ServerLevel)source.level()).setWeatherParameters(30000, 0, false, false);
        }
    }

    @Override
    public boolean canBePerformed(@NotNull LivingEntity source, @Nullable SpellTarget target) {
        if(source.level().getBiome(source.blockPosition()).value().getPrecipitationAt(source.blockPosition()) == Biome.Precipitation.NONE){
            return false;
        }

        return this.rain != source.level().isRaining();
    }
}
