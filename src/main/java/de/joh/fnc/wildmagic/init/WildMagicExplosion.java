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
     * Should the source of the magic be spared from the explosion?
     */
    public final boolean spareSource;

    /**
     * @param registryName  ID under which the upgrade can be recognized
     * @param frequency     How often does the entry appear in the random-selection-list?
     * @param targetsCaster Is the wild Magic source(true) or the spellTarget(false) targeted?
     * @param strength      Strength (Radius) of the explosion
     * @param breaksBlocks  Does the explosion break blocks, if {@link GameRules#RULE_MOBGRIEFING} = true?
     * @param spareSource   Should the source of the magic be spared from the explosion?
     */
    public WildMagicExplosion(@NotNull ResourceLocation registryName, int frequency, boolean targetsCaster, int strength, boolean breaksBlocks, boolean spareSource) {
        super(registryName, frequency, targetsCaster);
        this.strength = strength;
        this.breaksBlocks = breaksBlocks;
        this.spareSource = spareSource;
    }

    /**
     * @param registryName  ID under which the upgrade can be recognized
     * @param frequency     How often does the entry appear in the random-selection-list?
     * @param targetsCaster Is the wild Magic source(true) or the spellTarget(false) targeted?
     * @param strength      Strength (Radius) of the explosion
     * @param breaksBlocks  Does the explosion break blocks, if {@link GameRules#RULE_MOBGRIEFING} = true?
     */
    public WildMagicExplosion(@NotNull ResourceLocation registryName, int frequency, boolean targetsCaster, int strength, boolean breaksBlocks) {
        this(registryName, frequency, targetsCaster, strength, breaksBlocks, false);
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        if(targetsCaster || componentTag != SpellPartTags.HARMFUL){
            if(spareSource){
                return Quality.NEUTRAL;
            }
            return strength > 2 ? Quality.VERY_BAD : Quality.BAD;
        }
        return spareSource ? Quality.BAD :Quality.GOOD;
    }

    @Override
    protected void performWildMagic(@NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull SpellPartTags spellPartTag) {
        Vec3 coordinates;

        if(targetsCaster){
            coordinates = source.position();
        } else if (target != null){
            coordinates = target.getPosition();
        } else {
            return;
        }

        if (!source.level.isClientSide) {
            source.level.explode(spareSource ? source : null, null, null, coordinates.x, coordinates.y, coordinates.z, strength, false, (((ServerLevel)source.level).getServer().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && breaksBlocks) ? Explosion.BlockInteraction.BREAK : Explosion.BlockInteraction.NONE);
            //MAExplosion.make(source, (ServerLevel)source.level, coordinates.x, coordinates.y, coordinates.z, radius, damage, false, (((ServerLevel)source.level).getServer().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && breaksBlocks) ? Explosion.BlockInteraction.BREAK : Explosion.BlockInteraction.NONE);
        }
    }
}
