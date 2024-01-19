package de.joh.fnc.common.item;

import com.mna.api.faction.IFaction;
import com.mna.api.items.IFactionSpecific;
import com.mna.api.items.ITieredItem;
import com.mna.items.armor.IBrokenArmorReplaceable;
import com.mna.items.armor.ISetItem;
import de.joh.fnc.common.event.DamageEventHandler;
import de.joh.fnc.common.event.MagicEventHandler;
import de.joh.fnc.common.init.FactionInit;
import de.joh.fnc.common.item.material.ArmorMaterials;
import de.joh.fnc.common.util.RLoc;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

/**
 * An Armor for the {@link FactionInit#PALADIN Paladin Faction}
 * <br>Set Effekts (full armor must be worn):
 * <br> - - 50% Magic Damage
 * <br> - + Smite Duration
 * @see MagicEventHandler
 * @see DamageEventHandler
 * @author Joh0210
 */
public class DivineArmorItem extends ArmorItem implements ISetItem, ITieredItem<MischiefArmorItem>, IFactionSpecific, IBrokenArmorReplaceable<MischiefArmorItem> {
    private int tier = -1;
    private static final ResourceLocation divine_armor_set_bonus = RLoc.create("divine_armor_set_bonus");

    public DivineArmorItem(ArmorItem.Type type) {
        super(ArmorMaterials.DIVINE, type, new Item.Properties().rarity(Rarity.EPIC).stacksTo(1));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        ISetItem.super.addSetTooltip(tooltip);
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public void setCachedTier(int tier) {
        this.tier = tier;
    }

    @Override
    public int getCachedtier() {
        return this.tier;
    }

    @Override
    public ResourceLocation getSetIdentifier() {
        return divine_armor_set_bonus;
    }

    @Override
    public IFaction getFaction() {
        return FactionInit.PALADIN;
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return super.damageItem(stack, amount, entity, onBroken);
    }
}
