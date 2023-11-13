package de.joh.fnc.common.effect.harmful;

import de.joh.fnc.api.wildmagic.WildMagicHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * With this effect, an Entity causes a Wild Magic Effect if it casts a Spell.
 * <br> The higher this effect is, the more the Wild Magic Effect tends to be bad.
 * @see WildMagicHelper
 * @author Joh0210
 */
public class BadWildMagicMobEffect extends MobEffect {
    public BadWildMagicMobEffect() {
        super(MobEffectCategory.HARMFUL, 0xdaa520);
    }
}
