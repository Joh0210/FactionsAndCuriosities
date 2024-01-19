package de.joh.fnc.common.wildmagic;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import com.mna.tools.BlockUtils;
import de.joh.fnc.api.util.Quality;
import de.joh.fnc.api.wildmagic.WildMagicCOT;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public class FireWildMagic extends WildMagicCOT {
    /**
     * Duration of the Fire on an entity (in s)
     */
    private final int duration;

    /**
     * @param registryName  ID under which the upgrade can be recognized
     * @param frequency     How often does the entry appear in the random-selection-list?
     * @param targetsCaster Is the wild Magic source(true) or the spellTarget(false) targeted?
     * @param duration      Duration of the Fire on an entity (in s)
     */
    public FireWildMagic(@NotNull ResourceLocation registryName, int frequency, boolean targetsCaster, int duration) {
        super(registryName, frequency, targetsCaster);
        this.duration = duration;
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        if(targetsCaster || componentTag != SpellPartTags.HARMFUL){
            return duration > 10 ? Quality.VERY_BAD : Quality.BAD;
        }
        return Quality.GOOD;
    }

    @Override
    public boolean canBePerformed(@NotNull LivingEntity source, @Nullable SpellTarget target) {
        if(targetsCaster || target != null) {
            if (targetsCaster || target.getLivingEntity() != null) {
                return !(targetsCaster ? source : target.getLivingEntity()).fireImmune();
            }
            return true;
        }
        return false;
    }

    @Override
    protected void performWildMagic(@NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull SpellPartTags spellPartTag) {
        if(targetsCaster || target != null){
            if (targetsCaster || target.getLivingEntity() != null) {
                (targetsCaster ? source : target.getLivingEntity()).setSecondsOnFire(duration);

            } else if (target.getBlock() != null && !source.level().isEmptyBlock(target.getBlock())) {
                if (source.level().getBlockEntity(target.getBlock()) instanceof AbstractFurnaceBlockEntity furnace) {
                    int burnTime = (int)((float) 1050 * Math.max(duration, 1.0F));

                    Field f = ObfuscationReflectionHelper.findField(AbstractFurnaceBlockEntity.class, "litTime");
                    Field f2 = ObfuscationReflectionHelper.findField(AbstractFurnaceBlockEntity.class, "litDuration");

                    try {
                        if (f.getInt(furnace) < burnTime) {
                            f.set(furnace, burnTime);
                            f2.set(furnace, burnTime);
                            BlockState state = source.level().getBlockState(target.getBlock());
                            if (state.hasProperty(AbstractFurnaceBlock.LIT)) {
                                source.level().setBlock(target.getBlock(), state.setValue(AbstractFurnaceBlock.LIT, true), 3);
                            }
                        }
                    } catch (Throwable var11) {
                    }
                } else if (source.level().isEmptyBlock(target.getBlock().above())) {
                    BlockState fireState = BaseFireBlock.getState(source.level(), target.getBlock().above());
                    BlockUtils.placeBlock((ServerLevel) source.level(), target.getBlock().above(), Direction.UP, fireState, null);
                }
            }
        }
    }
}
