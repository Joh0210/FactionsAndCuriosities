package de.joh.fnc.common.effect.smite;

import de.joh.fnc.api.smite.SmiteMobEffect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BoomingSmite extends SmiteMobEffect {
    public BoomingSmite() {
        super(0xff8800);
    }

    @Override
    public void performSmite(Player source, LivingEntity target, int range, int magnitude, int duration, int precision) {
        Vec3 impact = target.position();
        if (target.level() instanceof ServerLevel serverLevel) {
            serverLevel.explode(precision >= 3 ? source : null, null, null, impact.x, impact.y, impact.z,
                    precision >= 3 ? magnitude/6.0f : magnitude/3.0f, precision == 0,
                    (((ServerLevel)source.level()).getServer().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && precision <= 1) ? Level.ExplosionInteraction.MOB : Level.ExplosionInteraction.NONE);
        }
    }
}

