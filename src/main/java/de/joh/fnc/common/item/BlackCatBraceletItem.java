package de.joh.fnc.common.item;

import com.mna.api.items.ChargeableItem;
import com.mna.items.artifice.curio.IPreEnchantedItem;
import de.joh.fnc.common.event.DamageEventHandler;
import de.joh.fnc.common.init.FactionInit;
import de.joh.fnc.api.util.CreativeModeTabInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

/**
 * If the wearer of the bracelet hits someone with an unarmed punch, it will cause a Wild Magic Effect on the Target.
 * The Target can not gain anny good or very_good effects of it
 * @see DamageEventHandler
 * @author Joh0210
 */
public class BlackCatBraceletItem extends ChargeableItem implements IPreEnchantedItem<BlackCatBraceletItem> {
    public BlackCatBraceletItem() {
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
