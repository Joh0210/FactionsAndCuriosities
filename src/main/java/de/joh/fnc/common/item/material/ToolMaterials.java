package de.joh.fnc.common.item.material;

import com.mna.items.ItemInit;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ToolMaterials {
    public static final ForgeTier WILD = new ForgeTier(
            4, 2031, 9.0F, 1.0F, 17,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.NETHERITE_INGOT));

    public static final ForgeTier LIVE_RIP = new ForgeTier(
            4, 1902, 9.0F, 3.0F, 26,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ItemInit.BRIMSTONE_CHARM.get()));

    //todo:
//    public static final ForgeTier BRIMSTONE = new ForgeTier(
//            4, 2902, 9.0F, 7.0F, 19,
//            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ItemInit.BRIMSTONE_CHARM.get()));
}
