package de.joh.fnc.common.wildmagic;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import com.mna.capabilities.playerdata.magic.PlayerMagicProvider;
import de.joh.fnc.common.init.EffectInit;
import de.joh.fnc.common.effect.beneficial.EmpoweredMobEffect;
import de.joh.fnc.common.event.MagicEventHandler;
import de.joh.fnc.api.util.Quality;
import de.joh.fnc.api.wildmagic.WildMagicPotionEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Increases all Modifiers of a spell (except Delay) by one step per Level for 5 min. (Beyond Maximum)
 * <br> The target must be a Player and an M&A magician
 * @see EmpoweredMobEffect
 * @see MagicEventHandler
 * @author Joh0210
 */
public class EmpoweredWildMagic extends WildMagicPotionEffect {
    /**
     * @param registryName  ID under which the upgrade can be recognized
     * @param frequency     How often does the entry appear in the random-selection-list?
     * @param targetsCaster Is the wild Magic source(true) or the spellTarget(false) targeted?
     * @param level         Which Level will the Effect have (staring at 1)
     */
    public EmpoweredWildMagic(@NotNull ResourceLocation registryName, int frequency, boolean targetsCaster, int level) {
        super(registryName, frequency, targetsCaster, 6000, level);
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
        if(targetsCaster || componentTag != SpellPartTags.HARMFUL){
            return level > 2 ? Quality.VERY_GOOD : Quality.GOOD;
        }
        return level > 2 ? Quality.VERY_BAD : Quality.BAD;
    }

    @Override
    public @NotNull MobEffect getMobEffect() {
        return EffectInit.EMPOWERED.get();
    }


}
