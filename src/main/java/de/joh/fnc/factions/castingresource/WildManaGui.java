package de.joh.fnc.factions.castingresource;

import com.mna.api.capabilities.resource.ICastingResourceGuiProvider;
import com.mna.gui.GuiTextures;
import com.mna.items.ItemInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class WildManaGui implements ICastingResourceGuiProvider {
    public ResourceLocation getTexture() {
        return GuiTextures.Hud.BARS;
    }

    public int getXPBarColor() {
        return 0xdaa520;
    }

    public int getBarColor() {
        return 0xdaa520;
    }

    public int getBarManaCostEstimateColor() {
        return 0xffd700;
    }

    public int getResourceNumericTextColor() {
        return -5394;
    }

    public int getBadgeSize() {
        return 64;
    }

    public int getFrameU() {
        return 0;
    }

    public int getFrameV() {
        return 24;
    }

    public int getFrameWidth() {
        return 153;
    }

    public int getFrameHeight() {
        return 24;
    }

    public ItemStack getBadgeItem() {
        return new ItemStack(ItemInit.BRIMSTONE_CHARM.get());
    }

    public int getBadgeItemOffsetY() {
        return 10;
    }

    public int getLevelDisplayY() {
        return this.getFrameHeight() - 2;
    }
}
