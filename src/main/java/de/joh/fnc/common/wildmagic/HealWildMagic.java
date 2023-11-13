package de.joh.fnc.common.wildmagic;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.api.util.Quality;
import de.joh.fnc.api.wildmagic.WildMagicCOT;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Heals the target to it's max HP
 * <br>Can only be performed if under halt the HP Maximum
 * @author Joh0210
 */
public class HealWildMagic extends WildMagicCOT {
    /**
     * @param registryName  ID under which the upgrade can be recognized
     * @param frequency     How often does the entry appear in the random-selection-list?
     * @param targetsCaster Is the wild Magic source(true) or the spellTarget(false) targeted?
     */
    public HealWildMagic(@NotNull ResourceLocation registryName, int frequency, boolean targetsCaster) {
        super(registryName, frequency, targetsCaster);
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        if(targetsCaster || componentTag != SpellPartTags.HARMFUL){
            return Quality.VERY_GOOD;
        }
        return Quality.VERY_BAD;
    }

    @Override
    protected void performWildMagic(@NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull SpellPartTags spellPartTag) {
        if(targetsCaster || (target != null && target.getLivingEntity() != null)){
            LivingEntity entity = (targetsCaster ? source : target.getLivingEntity());

            entity.setHealth(entity.getMaxHealth());
        }
    }

    @Override
    public boolean canBePerformed(@NotNull LivingEntity source, @Nullable SpellTarget target) {
        if(targetsCaster || (target != null && target.getLivingEntity() != null)){
            LivingEntity entity= (targetsCaster ? source : target.getLivingEntity());


            return entity.getHealth() < entity.getMaxHealth() / 2;
        }

        return false;
    }

    @Override
    public boolean requiresSpellLivingTarget() {
        return !targetsCaster;
    }
}
