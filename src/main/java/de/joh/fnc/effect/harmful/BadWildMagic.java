package de.joh.fnc.effect.harmful;

import de.joh.fnc.wildmagic.util.WildMagicHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * With this effect, an Entity causes a Wild Magic Effect if it casts a Spell.
 * <br> The higher this effect is, the more the Wild Magic Effect tends to be bad.
 * @see WildMagicHelper
 * @author Joh0210
 */
public class BadWildMagic  extends MobEffect {
    public BadWildMagic() {
        //todo: adjust color
        super(MobEffectCategory.NEUTRAL, -10496);
    }
}
