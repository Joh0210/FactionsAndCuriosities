package de.joh.fnc.common.faction.castingresource;

import com.mna.api.capabilities.resource.ICastingResourceGuiProvider;
import com.mna.api.capabilities.resource.SimpleCastingResource;
import com.mna.api.config.GeneralConfigValues;
import de.joh.fnc.common.faction.ResourceIDs;
import de.joh.fnc.common.util.RLoc;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class PaladinSwordCastingResource extends SimpleCastingResource {
    public PaladinSwordCastingResource() {
        super(GeneralConfigValues.TotalManaRegenTicks);
    }

    @Override
    public int getRegenerationRate(LivingEntity caster) {
        return (int)(this.ticks_for_regeneration * this.getRegenerationModifier(caster));
    }


    @Override
    public ResourceLocation getRegistryName() {
        return ResourceIDs.PALADIN_MANA_SWORD;
    }

    @Override
    public void setMaxAmountByLevel(int level) {
        int amount = 100;

        if(level > 60){
            amount += 100 + 20 * 45 + 15 * 15 + 10 * (level-60);
        }
        else if(level > 45){
            amount += 100 + 20 * 45 + 15 * (level-45);
        }
        else {
            amount += 100 + 20 * level;
        }

        this.setMaxAmount((float)(amount));
    }

    public static class ResourceGui implements ICastingResourceGuiProvider {
        public ResourceLocation getTexture() {
            // TODO: this texture is not being loaded, and the default MnA texture is being used instead.
            return RLoc.create("textures/gui/resource_bars.png");
        }

        public int getXPBarColor() {
            return 0x80ffffff;
        }

        public int getBarColor() {
            return 0xFF111111;
        }

        public int getBarManaCostEstimateColor() {
            return 0xff5A575A;
        }

        public int getResourceNumericTextColor() {
            return 0xFFEEF6F8;
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
            return 0;
        }

        public ItemStack getBadgeItem() {
            return new ItemStack(Items.NETHERITE_SWORD);
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
