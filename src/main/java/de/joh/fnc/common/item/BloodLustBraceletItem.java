package de.joh.fnc.common.item;

import com.mna.api.faction.IFaction;
import com.mna.api.items.ChargeableItem;
import com.mna.items.artifice.curio.IPreEnchantedItem;
import de.joh.fnc.common.event.MagicEventHandler;
import de.joh.fnc.common.init.FactionInit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

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
}
