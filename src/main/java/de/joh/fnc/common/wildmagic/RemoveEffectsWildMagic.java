package de.joh.fnc.common.wildmagic;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.api.util.Quality;
import de.joh.fnc.api.wildmagic.WildMagicCOT;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RemoveEffectsWildMagic extends WildMagicCOT {
    /**
     * @param registryName  ID under which the upgrade can be recognized
     * @param frequency     How often does the entry appear in the random-selection-list?
     * @param targetsCaster Is the wild Magic source(true) or the spellTarget(false) targeted?
     */
    public RemoveEffectsWildMagic(@NotNull ResourceLocation registryName, int frequency, boolean targetsCaster) {
        super(registryName, frequency, targetsCaster);
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        return Quality.NEUTRAL;
    }

    @Override
    public boolean canBePerformed(@NotNull LivingEntity source, @Nullable SpellTarget target) {
        if(!targetsCaster && (target == null || target.getLivingEntity() == null)){
            return false;
        }

        LivingEntity wildMagicTarget = targetsCaster ? source : target.getLivingEntity();

        return !wildMagicTarget.getActiveEffects().isEmpty();
    }

    @Override
    protected void performWildMagic(@NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull SpellPartTags spellPartTag) {
        if(!targetsCaster && (target == null || target.getLivingEntity() == null)){
            return;
        }

        LivingEntity wildMagicTarget = targetsCaster ? source : target.getLivingEntity();

        wildMagicTarget.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
    }
}
