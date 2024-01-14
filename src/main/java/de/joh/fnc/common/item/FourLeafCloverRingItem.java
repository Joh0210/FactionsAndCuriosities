package de.joh.fnc.common.item;

import com.mna.api.faction.IFaction;
import com.mna.api.items.ChargeableItem;
import com.mna.items.artifice.curio.IPreEnchantedItem;
import de.joh.fnc.api.util.CreativeModeTabInit;
import de.joh.fnc.common.event.MagicEventHandler;
import de.joh.fnc.common.init.FactionInit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

/**
 * Protects the Target from VERY_BAD Wild Magic/Spell Adjustment Effects
 * @see MagicEventHandler
 * @author Joh0210
 */
public class FourLeafCloverRingItem extends ChargeableItem implements IPreEnchantedItem<FourLeafCloverRingItem> {
    public FourLeafCloverRingItem() {
        super((new Item.Properties()).setNoRepair().tab(CreativeModeTabInit.FACTIONS_AND_CURIOSITIES).rarity(Rarity.UNCOMMON), 1000.0F);
    }

    protected boolean tickEffect(ItemStack stack, Player player, Level world, int slot, float mana, boolean selected) {
        return false;
    }

    protected boolean tickCurio() {
        return false;
    }

    @Override
    public IFaction getFaction() {
        return FactionInit.WILD;
    }
}
