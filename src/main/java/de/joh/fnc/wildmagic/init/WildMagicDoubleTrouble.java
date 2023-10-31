package de.joh.fnc.wildmagic.init;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.wildmagic.util.Quality;
import de.joh.fnc.wildmagic.util.WildMagic;
import de.joh.fnc.wildmagic.util.WildMagicHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Performs two Wild Magics
 * @author Joh0210
 */
public class WildMagicDoubleTrouble extends WildMagic {
    /**
     * @param registryName ID under which the upgrade can be recognized
     * @param frequency    How often does the entry appear in the random-selection-list?
     */
    public WildMagicDoubleTrouble(@NotNull ResourceLocation registryName, int frequency) {
        super(registryName, frequency);
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        return Quality.NEUTRAL;
    }

    @Override
    protected void performWildMagic(@NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull SpellPartTags spellPartTag) {
        WildMagicHelper.performRandomWildMagic(source, target, spellPartTag, (wm, s, t, comp) -> !(wm instanceof WildMagicDoubleTrouble));
        WildMagicHelper.performRandomWildMagic(source, target, spellPartTag, (wm, s, t, comp) -> !(wm instanceof WildMagicDoubleTrouble));
    }
}
