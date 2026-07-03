package de.joh.fnc.common.faction.castingresource;

import com.mna.api.capabilities.resource.SimpleCastingResource;
import com.mna.api.config.GeneralConfigValues;
import com.mna.api.capabilities.resource.ICastingResourceGuiProvider;
import de.joh.fnc.common.faction.ResourceIDs;
import de.joh.fnc.common.init.ItemInit;
import de.joh.fnc.common.util.RLoc;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class WildCastingResource extends SimpleCastingResource {
    public WildCastingResource() {
        super(GeneralConfigValues.TotalManaRegenTicks);
    }

    public int getRegenerationRate(LivingEntity caster) {
        return (int)((float)GeneralConfigValues.TotalManaRegenTicks * this.getRegenerationModifier(caster));
    }


    @Override
    public ResourceLocation getRegistryName() {
        return ResourceIDs.WILD_MANA;
    }

    @Override
    public void setMaxAmountByLevel(int level) {
        this.setMaxAmount((float)(50 + 25 + 15 * level));
    }

    public static class ResourceGui implements ICastingResourceGuiProvider {
        /**
         * Handled via Mixins since tha api does not provide a way to get the texture.
         * @see de.joh.fnc.mixins.CastingResourceMixin
         */
        public ResourceLocation getTexture() {
            return RLoc.create("gui/resource_bars.png");
        }

        public int getXPBarColor() {
            return 0x80ffffff;
        }

        public int getBarColor() {
            return 0xff006600;
        }

        public int getBarManaCostEstimateColor() {
            return 0xff009000;
        }

        public int getResourceNumericTextColor() {
            return 0xffb3871a;
        }

        public int getBadgeSize() {
            return 64;
        }

        public int getFrameU() {
            return 0;
        }

        public int getFrameWidth() {
            return 153;
        }

        public int getFrameHeight() {
            return 24;
        }

        public int getFrameV() {
            return 72;
        }

        public ItemStack getBadgeItem() {
            return new ItemStack(ItemInit.DICE.get());
        }

        public int getBadgeItemOffsetY() {
            return 9;
        }

        public int getFillWidth() {
            return 128;
        }

        public int getLevelDisplayY() {
            return this.getFrameHeight() - 1;
        }
    }
}
