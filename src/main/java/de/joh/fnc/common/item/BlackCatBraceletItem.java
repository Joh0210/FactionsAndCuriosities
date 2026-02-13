package de.joh.fnc.common.item;

import com.mna.api.faction.IFaction;
import com.mna.api.items.ChargeableItem;
import com.mna.items.artifice.curio.IPreEnchantedItem;
import de.joh.fnc.common.event.DamageEventHandler;
import de.joh.fnc.common.init.FactionInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * If the wearer of the bracelet hits someone with an unarmed punch, it will cause a Wild Magic Effect on the Target.
 * The Target can not gain anny good or very_good effects of it
 * @see DamageEventHandler
 * @author Joh0210
 */
public class BlackCatBraceletItem extends ChargeableItem implements IPreEnchantedItem<BlackCatBraceletItem> {
    public BlackCatBraceletItem() {
        super((new Item.Properties()).setNoRepair().rarity(Rarity.UNCOMMON), 1000.0F);
    }

    @Override
    protected boolean tickEffect(ItemStack stack, Player player, Level world, int slot, float mana, boolean selected) {
        return false;
    }

    @Override
    protected boolean tickCurio() {
        return false;
    }

    @Override
    public IFaction getFaction() {
        return FactionInit.WILD;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(Component.translatable("item.fnc.black_cat_bracelet.description").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("  "));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
