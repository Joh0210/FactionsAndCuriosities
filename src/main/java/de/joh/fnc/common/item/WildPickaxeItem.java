package de.joh.fnc.common.item;

import com.mna.api.items.IFactionSpecific;
import de.joh.fnc.common.item.material.ToolMaterials;
import de.joh.fnc.common.event.CommonEventHandler;
import de.joh.fnc.common.init.FactionInit;
import de.joh.fnc.api.util.CreativeModeTabInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * A pickaxe, that drops a random ore, when it mines one
 * @see CommonEventHandler
 * @author Joh0210
 */
public class WildPickaxeItem extends PickaxeItem implements IFactionSpecific {
    public WildPickaxeItem() {
        super(ToolMaterials.WILD, 1, 1f, new Item.Properties().tab(CreativeModeTabInit.FACTIONS_AND_CURIOSITIES));
    }

    /**
     * Item does not break
     */
    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return super.damageItem(stack, 0, entity, onBroken);
    }

    @Override
    public boolean isRepairable(@NotNull ItemStack stack) {
        return false;
    }

    public ResourceLocation getFaction() {
        return FactionInit.WILD.getRegistryName();
    }
}
