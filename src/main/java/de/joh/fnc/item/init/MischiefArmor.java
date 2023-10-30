package de.joh.fnc.item.init;

import com.mna.api.items.IFactionSpecific;
import com.mna.api.items.ITieredItem;
import com.mna.items.armor.IBrokenArmorReplaceable;
import com.mna.items.armor.ISetItem;
import de.joh.fnc.event.handler.MagicEventHandler;
import de.joh.fnc.factions.FactionInit;
import de.joh.fnc.utils.AttributeInit;
import de.joh.fnc.utils.CreativeModeTabInit;
import de.joh.fnc.utils.RLoc;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

/**
 * An Armor for the {@link FactionInit#WILD Wilde Courts Faction}
 * <br>Set Effekts (full armor must be worn):
 * <br> - +1 Wild Magic Luck
 * <br> - protects the wearer of VeryBad (and Bad?) Wild Magic Effects
 * @see MagicEventHandler
 * @author Joh0210
 */
public class MischiefArmor extends ArmorItem implements ISetItem, ITieredItem<MischiefArmor>, IFactionSpecific, IBrokenArmorReplaceable<MischiefArmor> {
    private static final AttributeModifier LUCK_BOOST = new AttributeModifier("mischief_armor_set_bonus", 1, AttributeModifier.Operation.ADDITION);
    private int tier = -1;
    private static final ResourceLocation mischief_armor_set_bonus = RLoc.create("mischief_armor_set_bonus");

    public MischiefArmor(EquipmentSlot pSlot) {
        super(ArmorMaterials.MISCHIEF, pSlot, new Item.Properties().tab(CreativeModeTabInit.FACTIONS_AND_CURIOSITIES).rarity(Rarity.EPIC).stacksTo(1));
    }

    @Override
    public void applySetBonus(LivingEntity living, EquipmentSlot... setSlots) {
        AttributeInstance wild_magic_luck = living.getAttribute(AttributeInit.WILD_MAGIC_LUCK.get());
        if(wild_magic_luck != null && !wild_magic_luck.hasModifier(LUCK_BOOST)){
            wild_magic_luck.addTransientModifier(LUCK_BOOST);
        }
    }

    @Override
    public void removeSetBonus(LivingEntity living, EquipmentSlot... setSlots) {
        AttributeInstance wild_magic_luck = living.getAttribute(AttributeInit.WILD_MAGIC_LUCK.get());
        if(wild_magic_luck != null){
            wild_magic_luck.removeModifier(LUCK_BOOST);
        }
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
        return mischief_armor_set_bonus;
    }

    @Override
    public ResourceLocation getFaction() {
        return FactionInit.WILD.getRegistryName();
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return super.damageItem(stack, amount * 3, entity, onBroken);
    }
}
