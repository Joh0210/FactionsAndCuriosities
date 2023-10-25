package de.joh.fnc.wildmagic.init;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.wildmagic.util.Quality;
import de.joh.fnc.wildmagic.util.WildMagicCOT;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This Wild Magic causes the target oder caster to explode. (Could cause block damage)
 * @author Joh0210
 */
public class WildMagicExplosion extends WildMagicCOT {
    /**
     * Strength (Radius) of the explosion
     */
    public final int strength;

    /**
     * Does the explosion break blocks, if {@link GameRules#RULE_MOBGRIEFING} = true?
     */
    public final boolean breaksBlocks;

    /**
     * @param registryName  ID under which the upgrade can be recognized
     * @param frequency     How often does the entry appear in the random-selection-list?
     * @param targetsCaster Is the wild Magic source(true) or the spellTarget(false) targeted?
     * @param strength      Strength (Radius) of the explosion
     * @param breaksBlocks  Does the explosion break blocks, if {@link GameRules#RULE_MOBGRIEFING} = true?
     */
    public WildMagicExplosion(@NotNull ResourceLocation registryName, int frequency, boolean targetsCaster, int strength, boolean breaksBlocks) {
        super(registryName, frequency, targetsCaster);
        this.strength = strength;
        this.breaksBlocks = breaksBlocks;
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        if(targetsCaster || componentTag != SpellPartTags.HARMFUL){
            return strength > 1 ? Quality.VERY_BAD : Quality.BAD;
        }
        return Quality.GOOD;
    }

    @Override
    public void performWildMagic(@NotNull LivingEntity source, @Nullable SpellTarget target) {
        Vec3 coordinates;

        if(targetsCaster){
            coordinates = source.position();
        } else if (target != null){
            coordinates = target.getPosition();
        } else {
            return;
        }

        if (!source.level.isClientSide) {
            source.level.explode(null, null, null, coordinates.x, coordinates.y, coordinates.z, strength, false, (((ServerLevel)source.level).getServer().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && breaksBlocks) ? Explosion.BlockInteraction.BREAK : Explosion.BlockInteraction.NONE);
            //MAExplosion.make(source, (ServerLevel)source.level, coordinates.x, coordinates.y, coordinates.z, radius, damage, false, (((ServerLevel)source.level).getServer().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && breaksBlocks) ? Explosion.BlockInteraction.BREAK : Explosion.BlockInteraction.NONE);
        }
    }
}
