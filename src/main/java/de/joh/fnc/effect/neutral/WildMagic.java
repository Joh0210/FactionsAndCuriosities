package de.joh.fnc.effect.neutral;

import de.joh.fnc.wildmagic.util.WildMagicHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * With this effect, an Entity causes a Wild Magic Effect if it casts a Spell.
 * @see WildMagicHelper
 * @author Joh0210
 */
public class WildMagic extends MobEffect {
    public WildMagic() {
        //todo: adjust color
        super(MobEffectCategory.NEUTRAL, -10496);
    }
}
