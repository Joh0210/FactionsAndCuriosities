package de.joh.fnc.common.item;

import com.mna.api.faction.IFaction;
import com.mna.api.items.IFactionSpecific;
import com.mna.api.items.ITieredItem;
import de.joh.fnc.api.smite.SmiteHelper;
import de.joh.fnc.api.spell.component.SmiteComponent;
import de.joh.fnc.api.util.CreativeModeTabInit;
import de.joh.fnc.common.event.DamageEventHandler;
import de.joh.fnc.common.init.FactionInit;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * Bow that allows {@link FactionInit#PALADIN Paladins} to use {@link SmiteComponent Smites} with Ranged Attacks
 * @see DamageEventHandler
 * @see SmiteHelper
 * @author Joh0210
 */
public class BlessedBowItem extends BowItem implements IFactionSpecific, ITieredItem<BlessedBowItem> {
    private int tier = -1;

    public BlessedBowItem() {
        super(new Item.Properties().tab(CreativeModeTabInit.FACTIONS_AND_CURIOSITIES).rarity(Rarity.EPIC).stacksTo(1).durability(100));
    }

    @Override
    public IFaction getFaction() {
        return FactionInit.PALADIN;
    }

    /**
     * Item does not break
     */
    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return super.damageItem(stack, 0, entity, onBroken);
    }

    @Override
    public void setCachedTier(int tier) {
        this.tier = tier;
    }

    @Override
    public int getCachedtier() {
        return this.tier;
    }

    public void releaseUsing(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player player) {
            // M&A
            this.usedByPlayer(player);

            // BOW:
            boolean flag = player.getAbilities().instabuild || pStack.getEnchantmentLevel(Enchantments.INFINITY_ARROWS) > 0;
            ItemStack itemstack = player.getProjectile(pStack);

            int i = this.getUseDuration(pStack) - pTimeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(pStack, pLevel, player, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = getPowerForTime(i);
                if (!((double)f < 0.1D)) {
                    boolean flag1 = player.getAbilities().instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, pStack, player));
                    if (!pLevel.isClientSide) {
                        ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                        AbstractArrow abstractarrow = arrowitem.createArrow(pLevel, itemstack, player);
                        abstractarrow = customArrow(abstractarrow);
                        abstractarrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * 3.0F, 1.0F);
                        if (f == 1.0F) {
                            abstractarrow.setCritArrow(true);
                        }

                        abstractarrow.getPersistentData().putBoolean("fnc_smite_arrow", true);

                        int j = pStack.getEnchantmentLevel(Enchantments.POWER_ARROWS);
                        if (j > 0) {
                            abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() + (double)j * 0.5D + 0.5D);
                        }

                        int k = pStack.getEnchantmentLevel(Enchantments.PUNCH_ARROWS);
                        if (k > 0) {
                            abstractarrow.setKnockback(k);
                        }

                        if (pStack.getEnchantmentLevel(Enchantments.FLAMING_ARROWS) > 0) {
                            abstractarrow.setSecondsOnFire(100);
                        }

                        pStack.hurtAndBreak(1, player, (p_40665_) -> p_40665_.broadcastBreakEvent(player.getUsedItemHand()));
                        if (flag1 || player.getAbilities().instabuild && (itemstack.is(Items.SPECTRAL_ARROW) || itemstack.is(Items.TIPPED_ARROW))) {
                            abstractarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                        }

                        pLevel.addFreshEntity(abstractarrow);
                    }

                    pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!flag1 && !player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            player.getInventory().removeItem(itemstack);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }
}
