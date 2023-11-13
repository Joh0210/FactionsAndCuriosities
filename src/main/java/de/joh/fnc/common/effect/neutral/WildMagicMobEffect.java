package de.joh.fnc.common.effect.neutral;

import de.joh.fnc.api.wildmagic.WildMagicHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * With this effect, an Entity causes a Wild Magic Effect if it casts a Spell.
 * @see WildMagicHelper
 * @author Joh0210
 */
public class WildMagicMobEffect extends MobEffect {
    public WildMagicMobEffect() {
        //todo: adjust color
        super(MobEffectCategory.NEUTRAL, -10496);
    }
}
