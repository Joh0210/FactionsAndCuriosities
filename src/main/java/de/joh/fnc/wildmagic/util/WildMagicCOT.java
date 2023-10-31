package de.joh.fnc.wildmagic.util;

import com.mna.api.spells.targeting.SpellTarget;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link WildMagic} Version, with {@link WildMagic#performWildMagic(LivingEntity, SpellTarget, com.mna.api.spells.SpellPartTags) procedure} could target the Spell-Target and the Caster.
 * By setting the {@link WildMagicCOT#targetsCaster targetsCaster} parameter, it is determined which of the two the procedure will be applied to.
 * @author Joh0210
 */
public abstract class WildMagicCOT extends WildMagic {
    /**
     * Is the wild Magic source(true) or the spellTarget(false) targeted?
     */
    public final boolean targetsCaster;

    /**
     * @param registryName  ID under which the upgrade can be recognized
     * @param frequency     How often does the entry appear in the random-selection-list?
     * @param targetsCaster Is the wild Magic source(true) or the spellTarget(false) targeted?
     */
    public WildMagicCOT(@NotNull ResourceLocation registryName, int frequency, boolean targetsCaster) {
        super(registryName, frequency);
        this.targetsCaster = targetsCaster;
    }

    @Override
    public boolean requiresSpellTarget() {
        return !targetsCaster || super.requiresSpellTarget();
    }
}
