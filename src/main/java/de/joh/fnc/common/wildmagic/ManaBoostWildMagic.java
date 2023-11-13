package de.joh.fnc.common.wildmagic;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import com.mna.capabilities.playerdata.magic.PlayerMagicProvider;
import com.mna.effects.EffectInit;
import de.joh.fnc.api.util.Quality;
import de.joh.fnc.api.wildmagic.WildMagicOtherPotionEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Increases the Mana of the caster for 10 min
 * @author Joh0210
 */
public class ManaBoostWildMagic extends WildMagicOtherPotionEffect {
    public ManaBoostWildMagic(@NotNull ResourceLocation registryName, int frequency, int level) {
        super(registryName, frequency, true, EffectInit.MANA_BOOST, 12000, level);
    }

    @Override
    public boolean canBePerformed(@NotNull LivingEntity source, @Nullable SpellTarget target) {
        if(!targetsCaster && target == null){
            return false;
        }

        LivingEntity wildMagicTarget = targetsCaster ? source : target.getLivingEntity();
        if(wildMagicTarget instanceof Player){
            AtomicBoolean isWizard = new AtomicBoolean(false);

            wildMagicTarget.getCapability(PlayerMagicProvider.MAGIC).ifPresent(magic -> isWizard.set(magic.getMagicLevel() >= 1));

            if(isWizard.get()){
                return super.canBePerformed(source, target);
            }
        }

        return false;
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        return level > 3 ? Quality.VERY_GOOD : Quality.GOOD;
    }
}
