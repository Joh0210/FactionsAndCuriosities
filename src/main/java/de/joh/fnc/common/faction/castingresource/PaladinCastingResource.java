package de.joh.fnc.common.faction.castingresource;

import com.mna.api.capabilities.resource.SimpleCastingResource;
import com.mna.api.config.GeneralConfigValues;
import de.joh.fnc.common.faction.ResourceIDs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class PaladinCastingResource extends SimpleCastingResource {
    public PaladinCastingResource() {
        super(GeneralConfigValues.TotalManaRegenTicks);
    }

    public int getRegenerationRate(LivingEntity caster) {
        return (int)((float) GeneralConfigValues.TotalManaRegenTicks * this.getRegenerationModifier(caster));
    }

    public ResourceLocation getRegistryName() {
        return ResourceIDs.PALADIN_MANA;
    }

    public void setMaxAmountByLevel(int level) {
        this.setMaxAmount((float)(100 + 20 * level));
    }
}
