package de.joh.fnc.item.init;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ToolMaterials {
    public static final ForgeTier WILD = new ForgeTier(
            4, 2031, 9.0F, 4.0F, 22,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.NETHERITE_INGOT));
}
