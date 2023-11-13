package de.joh.fnc.common.wildmagic;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import com.mna.effects.EffectInit;
import de.joh.fnc.api.util.Quality;
import de.joh.fnc.api.wildmagic.WildMagicPotionEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A Wild Magic that Repairs all Items in the Inventory of the Player
 * @author Joh0210
 */
public class RepairWildMagic extends WildMagicPotionEffect {
    public RepairWildMagic(@NotNull ResourceLocation registryName, int frequency) {
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

    @Override
    public boolean canBePerformed(@NotNull LivingEntity source, @Nullable SpellTarget target) {
        if(!(source instanceof Player)){
            return false;
        }

        return super.canBePerformed(source, target);
    }
}
