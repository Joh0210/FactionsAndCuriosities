package de.joh.fnc.wildmagic.init;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.wildmagic.util.Quality;
import de.joh.fnc.wildmagic.util.WildMagic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A wild magic effect that randomly teleports the source/spellTarget
 * @author Joh0210
 */
public class WildMagicRandomTp extends WildMagic {
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

    public WildMagicRandomTp(@NotNull ResourceLocation registryName, int frequency, int maxDistance, boolean targetsCaster) {
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
        //todo: Cast a Teleport Event
        if(!targetsCaster && target == null){
            return;
        }

        LivingEntity wildMagicTarget = targetsCaster ? source : target.getLivingEntity();
        int tries = TRIES;

        if(wildMagicTarget == null){
            return;
        }

        do {
            double rX = wildMagicTarget.getRandomX(this.maxDistance);
            double rY = wildMagicTarget.getY(this.maxDistance);
            double rZ = wildMagicTarget.getRandomZ(this.maxDistance);
            BlockPos.MutableBlockPos targetPos = new BlockPos.MutableBlockPos(rX, rY, rZ);

            while (targetPos.getY() > 0 && !wildMagicTarget.level.getBlockState(targetPos).getMaterial().blocksMotion()) {
                targetPos.move(Direction.DOWN);
            }

            BlockState blockstate = wildMagicTarget.level.getBlockState(targetPos);
            boolean blocksMotion = blockstate.getMaterial().blocksMotion();
            boolean isLava = blockstate.getFluidState().is(FluidTags.LAVA);
            if (blocksMotion && !isLava) {
                boolean teleported = wildMagicTarget.randomTeleport(rX, rY, rZ, true);
                if (teleported) {
                    if (!wildMagicTarget.isSilent()) {
                        wildMagicTarget.level.playSound(null, wildMagicTarget.xo, wildMagicTarget.yo, wildMagicTarget.zo, SoundEvents.ENDERMAN_TELEPORT, wildMagicTarget.getSoundSource(), 1.0F, 1.0F);
                        wildMagicTarget.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                    }

                    return;
                }
            }
            --tries;

        } while (tries >= 0);
    }

    @Override
    public boolean requiresSpellLivingTarget() {
        return !targetsCaster;
    }
}
