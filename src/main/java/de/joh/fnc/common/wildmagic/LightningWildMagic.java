package de.joh.fnc.common.wildmagic;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.api.util.Quality;
import de.joh.fnc.api.wildmagic.WildMagicCOT;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Strikes the Target with a Lightning
 * @author Joh0210
 */
public class LightningWildMagic extends WildMagicCOT {
    /**
     * @param registryName  ID under which the upgrade can be recognized
     * @param frequency     How often does the entry appear in the random-selection-list?
     * @param targetsCaster Is the wild Magic source(true) or the spellTarget(false) targeted?
     */
    public LightningWildMagic(@NotNull ResourceLocation registryName, int frequency, boolean targetsCaster) {
        super(registryName, frequency, targetsCaster);
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        if(targetsCaster || componentTag != SpellPartTags.HARMFUL){
            return Quality.BAD;
        }
        return Quality.GOOD;
    }

    @Override
    protected void performWildMagic(@NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull SpellPartTags spellPartTag) {
        Vec3 pos;
        if(targetsCaster){
            pos = source.position();
        }
        else if (target != null){
            pos = target.getPosition();
        } else {
            return;
        }

        LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(source.level);
        if(lightningbolt == null) return;
        lightningbolt.setPos(pos);
        source.level.addFreshEntity(lightningbolt);
    }
}
