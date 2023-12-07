package de.joh.fnc.compat.dmnr.common.wildmagic;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.dmnr.api.item.DragonMageArmorItem;
import de.joh.dmnr.common.init.EffectInit;
import de.joh.fnc.api.util.Quality;
import de.joh.fnc.api.wildmagic.WildMagicOtherPotionEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gives the Caster the Ultimate Armor Effect if it wears the Dragon Mage Armor
 * @author Joh0210
 */
public class UltimateArmorWildMagic extends WildMagicOtherPotionEffect {
    public UltimateArmorWildMagic(@NotNull ResourceLocation registryName, int frequency, int duration) {
        super(registryName, frequency, true, EffectInit.ULTIMATE_ARMOR, duration, 1);
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        return Quality.VERY_GOOD;
    }

    @Override
    public boolean canBePerformed(@NotNull LivingEntity source, @Nullable SpellTarget target) {
        if(source.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof DragonMageArmorItem dmArmor && dmArmor.isSetEquipped(source)) {
            return super.canBePerformed(source, target);
        }
        return false;
    }
}
