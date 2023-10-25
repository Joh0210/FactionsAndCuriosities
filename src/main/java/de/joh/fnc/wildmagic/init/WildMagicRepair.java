package de.joh.fnc.wildmagic.init;

import com.mna.api.spells.SpellPartTags;
import com.mna.effects.EffectInit;
import de.joh.fnc.wildmagic.util.Quality;
import de.joh.fnc.wildmagic.util.WildMagicPotionEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import org.jetbrains.annotations.NotNull;

/**
 * A Wild Magic that Repairs all Items in the Inventory of the Player
 * @author Joh0210
 */
public class WildMagicRepair extends WildMagicPotionEffect {
    public WildMagicRepair(@NotNull ResourceLocation registryName, int frequency) {
        super(registryName, frequency, true, 12000 , 1);
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        return Quality.VERY_GOOD;
    }

    @Override
    public @NotNull MobEffect getMobEffect() {
        return EffectInit.REPAIR.get();
    }
}
