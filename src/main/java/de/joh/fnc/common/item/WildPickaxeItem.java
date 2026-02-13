package de.joh.fnc.common.item;

import com.mna.api.faction.IFaction;
import com.mna.api.items.IFactionSpecific;
import de.joh.fnc.common.event.CommonEventHandler;
import de.joh.fnc.common.init.FactionInit;
import de.joh.fnc.common.item.material.ToolMaterials;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

/**
 * A pickaxe, that drops a random ore, when it mines one
 * @see CommonEventHandler
 * @author Joh0210
 */
public class WildPickaxeItem extends PickaxeItem implements IFactionSpecific {
    public WildPickaxeItem() {
        super(ToolMaterials.WILD, 1, -2.9F, new Item.Properties());
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

    @Override
    public IFaction getFaction() {
        return FactionInit.WILD;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(Component.translatable("item.fnc.wild_pickaxe.description").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
