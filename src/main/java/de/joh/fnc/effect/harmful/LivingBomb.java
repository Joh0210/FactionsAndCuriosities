package de.joh.fnc.effect.harmful;

import de.joh.fnc.utils.CommonConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import org.jetbrains.annotations.NotNull;

/**
 * Lets the target explode every 10 seconds.
 * <br>The amplifier determines the strength of the explosion.
 * @author Joh0210
 */
public class LivingBomb extends MobEffect {

    public LivingBomb() {
        super(MobEffectCategory.HARMFUL, 0xff0000);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        if(!livingEntity.level.isClientSide) {
            livingEntity.level.explode(null, null, null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 2 * amplifier + 1, false, (((ServerLevel) livingEntity.level).getServer().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && CommonConfig.LIVING_BOMB_BREAK_BLOCKS.get()) ? Explosion.BlockInteraction.BREAK : Explosion.BlockInteraction.NONE);
        }
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 200 == 0;
    }
}
