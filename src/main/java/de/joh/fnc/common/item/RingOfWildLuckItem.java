package de.joh.fnc.common.item;

import com.mna.api.faction.IFaction;
import com.mna.api.items.IFactionSpecific;
import com.mna.api.items.TieredItem;
import de.joh.fnc.api.util.AttributeInit;
import de.joh.fnc.common.init.FactionInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

/**
 * Allows players from other factions to trigger Wild Magic events and also gain the luck of WildMagic
 * @author Joh0210
 */
public class RingOfWildLuckItem extends TieredItem implements IFactionSpecific, ICurioItem {
    private static final AttributeModifier LUCK_BOOST = new AttributeModifier("ring_of_wild_luck_item_bonus", 1, AttributeModifier.Operation.ADDITION);

    public RingOfWildLuckItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(slotContext.entity() instanceof Player player){
            AttributeInstance wild_magic_luck = player.getAttribute(AttributeInit.WILD_MAGIC_LUCK.get());
            if(wild_magic_luck != null && !wild_magic_luck.hasModifier(LUCK_BOOST)){
                wild_magic_luck.addTransientModifier(LUCK_BOOST);
            }
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity() instanceof Player player){
            AttributeInstance wild_magic_luck = player.getAttribute(AttributeInit.WILD_MAGIC_LUCK.get());
            if(wild_magic_luck != null){
                wild_magic_luck.removeModifier(LUCK_BOOST);
            }
        }
    }

    @Override
    public IFaction getFaction() {
        return FactionInit.WILD;
    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(Component.translatable("item.fnc.ring_of_wild_luck.description").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("  "));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
