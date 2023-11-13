package de.joh.fnc.api.spelladjustment;

import com.mna.api.events.SpellCastEvent;
import com.mna.api.spells.SpellPartTags;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.common.utils.RLoc;
import de.joh.fnc.api.util.Quality;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;

public abstract class SpellAdjustment extends ForgeRegistryEntry<SpellAdjustment> {
    /**
     * How often does the entry appear in the random-selection-list?
     * todo Can be set via Config. If set to 0, it will not appear in the random-selection-list
     */
    public final int frequency;

    /**
     * How good is the effect for the source entity?
     */
    public @NotNull
    abstract Quality getQuality(SpellPartTags componentTag);

    /**
     * Instance to ensure null save calls
     */
    public static SpellAdjustment INSTANCE = new SpellAdjustment(RLoc.create("spelladjustment/none"), 0) {
        @Override
        public @NotNull Quality getQuality(SpellPartTags spellPartTag) {
            return Quality.NEUTRAL;
        }

        @Override
        protected void performSpellAdjustment(@NotNull SpellCastEvent spellCastEvent) {
            FactionsAndCuriosities.LOGGER.error("performSpellAdjustment of the Spell Adjustment phantom instance was called");
        }
    };

    /**
     * @param registryName ID under which the upgrade can be recognized
     * @param frequency How often does the entry appear in the random-selection-list?
     */
    public SpellAdjustment(@NotNull ResourceLocation registryName, int frequency){
        this.frequency = frequency;
        this.setRegistryName(registryName);
    }

    protected abstract void performSpellAdjustment(@NotNull SpellCastEvent spellCastEvent);

    public boolean canBePerformed(@NotNull SpellCastEvent spellCastEvent){
        return true;
    }

    @Override
    public String toString() {
        return this.getRegistryName().toString();
    }
}
