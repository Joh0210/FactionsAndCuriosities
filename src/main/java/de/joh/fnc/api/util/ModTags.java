package de.joh.fnc.api.util;

import de.joh.fnc.common.util.RLoc;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ModTags {
    public static List<Item> getItemTagContents(ResourceLocation tagID) {
        try {
            ITag<Item> tag = ForgeRegistries.ITEMS.tags().getTag(ForgeRegistries.ITEMS.tags().createTagKey(tagID));
            if (tag != null) {
                return tag.stream().collect(Collectors.toList());
            }
        } catch (Exception var2) {
        }

        return new ArrayList<>();
    }

    public static List<Block> getBlockTagContents(ResourceLocation tagID) {
        try {
            ITag<Block> tag = ForgeRegistries.BLOCKS.tags().getTag(ForgeRegistries.BLOCKS.tags().createTagKey(tagID));
            if (tag != null) {
                return tag.stream().collect(Collectors.toList());
            }
        } catch (Exception var2) {
        }

        return new ArrayList<>();
    }

    public static Block getRandomBlocks(ResourceLocation tag) {
        try {
            Random random = new Random();
            List<Block> list = getBlockTagContents(tag);
            return list.get(random.nextInt(list.size()));
        } catch (Exception var3) {
            return null;
        }
    }

    public static boolean isBlockIn(Block block, ResourceLocation tag) {
        try {
            return getBlockTagContents(tag).contains(block);
        } catch (Exception var3) {
            return false;
        }
    }

    public static boolean isItemIn(Item item, ResourceLocation tag) {
        try {
            return getItemTagContents(tag).contains(item);
        } catch (Exception var3) {
            return false;
        }
    }

    public static class Blocks {
        //public static ResourceLocation ORES = new ResourceLocation("forge","ores");
        public static ResourceLocation ORES = RLoc.create("wild_pickaxe_ore");
    }

    public static class Items {
    }
}
