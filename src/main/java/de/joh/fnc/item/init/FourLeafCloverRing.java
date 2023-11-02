package de.joh.fnc.item.init;

import com.mna.api.items.ChargeableItem;
import com.mna.items.artifice.curio.IPreEnchantedItem;
import de.joh.fnc.event.handler.MagicEventHandler;
import de.joh.fnc.factions.FactionInit;
import de.joh.fnc.utils.CreativeModeTabInit;
import net.minecraft.resources.ResourceLocation;
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
public class FourLeafCloverRing extends ChargeableItem implements IPreEnchantedItem<de.joh.fnc.item.init.BlackCatBracelet> {
    public FourLeafCloverRing() {
        super((new Item.Properties()).setNoRepair().tab(CreativeModeTabInit.FACTIONS_AND_CURIOSITIES).rarity(Rarity.UNCOMMON), 1000.0F);
    }

    protected boolean tickEffect(ItemStack stack, Player player, Level world, int slot, float mana, boolean selected) {
        return false;
    }

    protected boolean tickCurio() {
        return false;
    }

    public ResourceLocation getFaction() {
        return FactionInit.WILD.getRegistryName();
    }
}
