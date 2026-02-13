package de.joh.fnc.common.item;

import com.mna.api.faction.IFaction;
import com.mna.api.items.ChargeableItem;
import com.mna.items.artifice.curio.IPreEnchantedItem;
import de.joh.fnc.common.event.MagicEventHandler;
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
 * If the wearer uses smites, they gain a small regeneration effect.
 * @see MagicEventHandler
 * @author Joh0210
 */
public class BloodLustBraceletItem extends ChargeableItem implements IPreEnchantedItem<BlackCatBraceletItem> {
        public BloodLustBraceletItem() {
                super((new Item.Properties()).setNoRepair().rarity(Rarity.UNCOMMON), 1000.0F);
        }

        protected boolean tickEffect(ItemStack stack, Player player, Level world, int slot, float mana, boolean selected) {
                return false;
        }

        protected boolean tickCurio() {
                return false;
        }

        @Override
        public IFaction getFaction() {
                return FactionInit.PALADIN;
        }


        @OnlyIn(Dist.CLIENT)
        @Override
        public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
                tooltip.add(Component.translatable("item.fnc.blood_lust_bracelet.description").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
                tooltip.add(Component.literal("  "));
                super.appendHoverText(stack, world, tooltip, flag);
        }
}
