package de.joh.fnc.effect.beneficial;

import de.joh.fnc.wildmagic.util.WildMagicHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/**
 * With this effect, an Entity causes a Wild Magic Effect if it casts a Spell.
 * <br> The higher this effect is, the more the Wild Magic Effect tends to be positive.
 * @see WildMagicHelper
 * @author Joh0210
 */
public class GoodWildMagic extends MobEffect {
    public GoodWildMagic() {
        super(MobEffectCategory.BENEFICIAL, 0xdaa520);
    }
}
