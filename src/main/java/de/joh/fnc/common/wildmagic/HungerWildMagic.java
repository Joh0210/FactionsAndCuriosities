package de.joh.fnc.common.wildmagic;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.api.util.Quality;
import de.joh.fnc.api.wildmagic.WildMagicCOT;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Refills/empties the player's hunger
 * @author Joh0210
 */
public class HungerWildMagic extends WildMagicCOT {
    /**
     * Should the hunger be set to 1 (true) or filled (false)?
     */
    public final boolean emptyHunger;

    /**
     * @param registryName   ID under which the upgrade can be recognized
     * @param frequency      How often does the entry appear in the random-selection-list?
     * @param targetsCaster  Is the wild Magic source(true) or the spellTarget(false) targeted?
     * @param emptyHunger Should the hunger be set to 1 (true) or filled (false)?
     */
    public HungerWildMagic(@NotNull ResourceLocation registryName, int frequency, boolean targetsCaster, boolean emptyHunger) {
        super(registryName, frequency, targetsCaster);
        this.emptyHunger = emptyHunger;
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        if(targetsCaster || componentTag != SpellPartTags.HARMFUL){
            return emptyHunger ? Quality.BAD : Quality.GOOD;
        }
        return emptyHunger ? Quality.GOOD : Quality.BAD;
    }

    @Override
    protected void performWildMagic(@NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull SpellPartTags spellPartTag) {
        if((targetsCaster || target != null) && (targetsCaster ? source : target.getLivingEntity()) instanceof Player player){
            player.getFoodData().setFoodLevel(emptyHunger ? 1 : 20);
            player.getFoodData().setSaturation(emptyHunger ? 0.5f : 1.0f);
        }
    }

    @Override
    public boolean canBePerformed(@NotNull LivingEntity source, @Nullable SpellTarget target) {
        if(targetsCaster){
            return source instanceof Player;
        } else {
            return target != null && target.getLivingEntity() instanceof Player;
        }
    }

    @Override
    public boolean requiresSpellLivingTarget() {
        return !targetsCaster;
    }
}
