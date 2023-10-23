package de.joh.fnc.utils;

import de.joh.fnc.item.ItemInit;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Tabs for the Items of this Mod
 * @author Joh0210
 */
public class CreativeModeTabInit {
    public static final CreativeModeTab FACTIONS_AND_CURIOSITIES = new net.minecraft.world.item.CreativeModeTab("factions_and_curiosities"){
        @Override
        public @NotNull ItemStack makeIcon(){
            return new ItemStack(ItemInit.DICE.get());
        }
    };
}
