package de.joh.fnc.common.effect.neutral;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * With this effect, an Entity cannot be the cause of Wild Magic
 * @author Joh0210
 */
public class WildMagicCooldownMobEffect extends MobEffect {
    public WildMagicCooldownMobEffect() {
        //todo: adjust color
        super(MobEffectCategory.NEUTRAL, 0);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }
}
