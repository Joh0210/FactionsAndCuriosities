package de.joh.fnc.common.item;

import com.mna.api.faction.IFaction;
import com.mna.items.artifice.curio.IPreEnchantedItem;
import de.joh.fnc.common.event.MagicEventHandler;
import de.joh.fnc.common.init.FactionInit;
import de.joh.fnc.common.util.CommonConfig;
import de.joh.tnl.api.item.ChargeableDragonMagicItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Increases the maximum smites damage by {@link CommonConfig#SMITING_RING_MAX_DAMAGE_BONUS}
 * <br>Dragon Magic: Increases it by another {@link CommonConfig#SMITING_RING_DRAGON_MAGIC_MAX_DAMAGE_BONUS}
 * @see MagicEventHandler
 * @author Joh0210
 */
public class SmitingRingItem extends ChargeableDragonMagicItem implements IPreEnchantedItem<SmitingRingItem> {
        public SmitingRingItem() {
                super((new Properties()).setNoRepair().rarity(Rarity.RARE), 1000.0F);
        }

        @Override
        public String dragonMagicID() {
                return "item.fnc.smiting_ring.dragonmagic";
        }

        /**
         * @param player Wearer of the ring
         * @return By how much the ring raises the maximum damage of the Smites of the player
         */
        public int getMaxDamageBonus(Player player) {
                return CommonConfig.SMITING_RING_MAX_DAMAGE_BONUS.get() + (this.hasDragonMagic(player) ? CommonConfig.SMITING_RING_DRAGON_MAGIC_MAX_DAMAGE_BONUS.get() : 0);
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
                tooltip.add(Component.translatable("item.fnc.smiting_ring.description", CommonConfig.SMITING_RING_MAX_DAMAGE_BONUS.get()).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
                tooltip.add(Component.literal("  "));
                super.appendHoverText(stack, world, tooltip, flag);
        }
}
