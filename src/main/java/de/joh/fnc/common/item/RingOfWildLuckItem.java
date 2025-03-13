package de.joh.fnc.common.item;

import com.mna.api.faction.IFaction;
import com.mna.api.items.IFactionSpecific;
import com.mna.api.items.TieredItem;
import de.joh.fnc.api.util.AttributeInit;
import de.joh.fnc.common.init.FactionInit;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

/**
 * Allows players from other factions to trigger Wild Magic events and also gain the luck of WildMagic
 * @author Joh0210
 */
public class RingOfWildLuckItem extends TieredItem implements IFactionSpecific, ICurioItem {
    private static final AttributeModifier LUCK_BOOST = new AttributeModifier("ring_of_wild_luck_item_bonus", 1, AttributeModifier.Operation.ADDITION);

    public RingOfWildLuckItem() {
        super(new Item.Properties());
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
}
