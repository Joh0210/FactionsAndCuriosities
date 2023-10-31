package de.joh.fnc.wildmagic.util;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.utils.RLoc;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Wild magic effect that can be performed by on an entity.
 * @author Joh0210
 */
public abstract class WildMagic extends ForgeRegistryEntry<WildMagic> {
    /**
     * How often does the entry appear in the random-selection-list?
     * todo Can be set via Config. If set to 0, it will not appear in the random-selection-list
     */
    public final int frequency;

    /**
     * How good is the effect for the source entity?
     */
    public @NotNull abstract Quality getQuality(SpellPartTags componentTag);

    /**
     * Instance to ensure null save calls
     */
    public static WildMagic INSTANCE = new WildMagic(RLoc.create("wildmagic/none"), 0) {
        @Override
        public @NotNull Quality getQuality(SpellPartTags spellPartTag) {
            return Quality.NEUTRAL;
        }

        @Override
        protected void performWildMagic(@NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull SpellPartTags spellPartTag) {
            FactionsAndCuriosities.LOGGER.error("performWildMagic of the Wild Magic phantom instance was called");
        }
    };

    /**
     * @param registryName ID under which the upgrade can be recognized
     * @param frequency How often does the entry appear in the random-selection-list?
     */
    public WildMagic(@NotNull ResourceLocation registryName, int frequency){
        this.frequency = frequency;
        this.setRegistryName(registryName);
    }

    /**
     * Procedure of the wild magic effect being executed
     *
     * @param source       Source from which wild magic emanates
     * @param target       Target of the spell that triggers the wild magic. Null if Wild Magic was not triggered by a spell
     * @param spellPartTag Tag of the Spell
     */
    protected abstract void performWildMagic(@NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull SpellPartTags spellPartTag);

    /**
     * Condition under which this wild magic effect can be executed
     * @param source Source from which wild magic emanates
     * @param target Target of the spell that triggers the wild magic. Null if Wild Magic was not triggered by a spell
     * @return true if this effect can be executed
     */
    public boolean canBePerformed(@NotNull LivingEntity source, @Nullable SpellTarget target){
        if (requiresSpellTarget() && target == null){
            return false;
        }
        if (requiresSpellLivingTarget() && (target == null || !target.isLivingEntity())){
            return false;
        }
        if (requiresSpellBlockTarget() && (target == null || !target.isBlock())){
            return false;
        }

        return true;
    }

    public boolean requiresSpellTarget(){
        return requiresSpellBlockTarget() || requiresSpellLivingTarget();
    }

    public boolean requiresSpellLivingTarget(){
        return false;
    }

    public boolean requiresSpellBlockTarget(){
        return false;
    }

    @Override
    public String toString() {
        return this.getRegistryName().toString();
    }
}
