package de.joh.fnc.common.wildmagic;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.api.util.Quality;
import de.joh.fnc.api.wildmagic.WildMagicCOT;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

/**
 * Refills armor slots with damaged armor of the given Type
 * @author Joh0210
 */
public class ArmoredWildMagic extends WildMagicCOT {
    private final Type type;

    /**
     * @param registryName  ID under which the upgrade can be recognized
     * @param frequency     How often does the entry appear in the random-selection-list?
     * @param targetsCaster Is the wild Magic source(true) or the spellTarget(false) targeted?
     * @param type          Type of Armor
     */
    public ArmoredWildMagic(@NotNull ResourceLocation registryName, int frequency, boolean targetsCaster, Type type) {
        super(registryName, frequency, targetsCaster);
        this.type = type;
    }

    @Override
    public @NotNull Quality getQuality(SpellPartTags componentTag) {
        if(targetsCaster || componentTag != SpellPartTags.HARMFUL){
            return this.type.ordinal() > Type.IRON.ordinal() ? Quality.VERY_GOOD : Quality.GOOD;
        }
        return  this.type.ordinal() > Type.IRON.ordinal() ? Quality.VERY_BAD : Quality.BAD;
    }

    @Override
    protected void performWildMagic(@NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull SpellPartTags spellPartTag) {
        Random random = new Random();

        if(targetsCaster || (target != null && target.getLivingEntity() != null)) {
            LivingEntity entity = targetsCaster ? source : target.getLivingEntity();
            for(EquipmentSlot slot : EquipmentSlot.values()){
                if(slot.getType() == EquipmentSlot.Type.ARMOR && entity.getItemBySlot(slot).isEmpty() && type.getItemBySlot(slot) instanceof ArmorItem armor){
                    ItemStack armorStack = new ItemStack(armor);
                    armorStack.setDamageValue(armorStack.getMaxDamage() * (random.nextInt(351) + random.nextInt(351) + 280) / 1000);
                    entity.setItemSlot(slot, armorStack);
                }
            }
        }
    }

    @Override
    public boolean canBePerformed(@NotNull LivingEntity source, @Nullable SpellTarget target) {
        if(targetsCaster || (target != null && target.getLivingEntity() != null)) {
            LivingEntity entity = targetsCaster ? source : target.getLivingEntity();
            for(EquipmentSlot slot : EquipmentSlot.values()){
               if(slot.getType() == EquipmentSlot.Type.ARMOR && entity.getItemBySlot(slot).isEmpty()){
                   return true;
               }
            }
        }
        return false;
    }

    public enum Type{
        GOLD(Items.GOLDEN_BOOTS, Items.GOLDEN_LEGGINGS, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_HELMET),
        IRON(Items.IRON_BOOTS, Items.IRON_LEGGINGS, Items.IRON_CHESTPLATE, Items.IRON_HELMET),
        DIAMOND(Items.DIAMOND_BOOTS, Items.DIAMOND_LEGGINGS, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_HELMET);

        private final Item feet;
        private final Item legs;
        private final Item chest;
        private final Item head;


        Type(Item feet, Item legs, Item chest, Item head){
            this.feet = feet;
            this.legs = legs;
            this.chest = chest;
            this.head = head;
        }

        @Nullable
        public Item getItemBySlot(EquipmentSlot slot){
            return switch (slot){
                case FEET  -> feet;
                case LEGS -> legs;
                case CHEST -> chest;
                case HEAD -> head;
                default -> null;
            };
        }
    }
}
