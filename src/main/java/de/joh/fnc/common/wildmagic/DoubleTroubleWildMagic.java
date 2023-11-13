package de.joh.fnc.common.wildmagic;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.api.util.Quality;
import de.joh.fnc.api.wildmagic.WildMagic;
import de.joh.fnc.api.wildmagic.WildMagicHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Performs two Wild Magics
 * @author Joh0210
 */
public class DoubleTroubleWildMagic extends WildMagic {
    /**
     * @param registryName ID under which the upgrade can be recognized
     * @param frequency    How often does the entry appear in the random-selection-list?
     */
    public DoubleTroubleWildMagic(@NotNull ResourceLocation registryName, int frequency) {
        super(registryName, frequency);
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        return Quality.NEUTRAL;
    }

    @Override
    protected void performWildMagic(@NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull SpellPartTags spellPartTag) {
        WildMagicHelper.performRandomWildMagic(source, target, spellPartTag, (wm, s, t, comp) -> !(wm instanceof DoubleTroubleWildMagic));
        WildMagicHelper.performRandomWildMagic(source, target, spellPartTag, (wm, s, t, comp) -> !(wm instanceof DoubleTroubleWildMagic));
    }
}
