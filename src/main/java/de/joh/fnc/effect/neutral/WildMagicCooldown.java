package de.joh.fnc.effect.neutral;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * With this effect, an Entity cannot be the cause of Wild Magic
 * @author Joh0210
 */
public class WildMagicCooldown extends MobEffect {
    /**
     * How many ticks should be waited, till Wild Magic can be applied again?
     * <br>todo: make it adjustable (minimum of 100 ticks if deactivated)
     */
    public static final int WILD_MAGIC_COOLDOWN = 100;

    public WildMagicCooldown() {
        //todo: adjust color
        super(MobEffectCategory.NEUTRAL, 0);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }
}
