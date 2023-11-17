package de.joh.fnc.common.wildmagic;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import com.mna.tools.TeleportHelper;
import de.joh.fnc.api.util.Quality;
import de.joh.fnc.api.wildmagic.WildMagic;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A wild magic effect that randomly teleports the source/spellTarget
 * @author Joh0210
 */
public class RandomTpWildMagic extends WildMagic {
    /**
     * How often should the TP be tried.
     */
    public static final int TRIES = 10;

    /**
     * The max distance of the max TP;
     */
    public final int maxDistance;

    /**
     * Is the wild Magic source(true) or the spellTarget(false) targeted?
     */
    public final boolean targetsCaster;

    public RandomTpWildMagic(@NotNull ResourceLocation registryName, int frequency, int maxDistance, boolean targetsCaster) {
        super(registryName, frequency);
        this.maxDistance = maxDistance;
        this.targetsCaster = targetsCaster;
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags spellPartTag) {
        return Quality.BAD;
    }

    @Override
    protected void performWildMagic(@NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull SpellPartTags spellPartTag) {
        if(!targetsCaster && target == null){
            return;
        }

        LivingEntity wildMagicTarget = targetsCaster ? source : target.getLivingEntity();

        if(wildMagicTarget == null){
            return;
        }

        TeleportHelper.randomTeleport(wildMagicTarget, this.maxDistance, TRIES);
    }

    @Override
    public boolean requiresSpellLivingTarget() {
        return !targetsCaster;
    }
}
