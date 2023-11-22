package de.joh.fnc.common.faction.castingresource;

import com.mna.api.capabilities.resource.SimpleCastingResource;
import com.mna.config.GeneralModConfig;
import de.joh.fnc.common.faction.ResourceIDs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class WildCastingResource extends SimpleCastingResource {
    public WildCastingResource() {
        super(GeneralModConfig.MANA_TICKS_FOR_REGEN.get());
    }

    public int getRegenerationRate(LivingEntity caster) {
        return (int)((float) GeneralModConfig.MANA_TICKS_FOR_REGEN.get() * this.getRegenerationModifier(caster));
    }

    public ResourceLocation getRegistryName() {
        return ResourceIDs.WILD_MANA;
    }

    public void setMaxAmountByLevel(int level) {
        this.setMaxAmount((float)(100 + 20 * level));
    }
}
