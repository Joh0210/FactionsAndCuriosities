package de.joh.fnc.common.item;

import com.mna.api.faction.IFaction;
import com.mna.api.items.ChargeableItem;
import com.mna.items.artifice.curio.IPreEnchantedItem;
import de.joh.fnc.api.wildmagic.WildMagicHelper;
import de.joh.fnc.common.init.FactionInit;
import de.joh.fnc.common.init.ItemInit;
import de.joh.fnc.common.util.CommonConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

/**
 * Whenever you deal damage, there's a 25% chance that the damage will be reapplied. (One success, and it repeats.)
 * Similarly, with potion effects, there's a 25% chance that the effect will be increased/decreased by 1.
 * @author Joh0210
 */
public class SecondChanceItem extends ChargeableItem implements IPreEnchantedItem<SecondChanceItem> {
    public final SecondChanceItem.Type type;

    public SecondChanceItem(SecondChanceItem.Type type) {
        super((new Properties()).setNoRepair().rarity(Rarity.RARE).stacksTo(1), 500.0F);
        this.type = type;
    }

    public static int secondChances(Player player) {
        Random random = new Random();

        int secondChances = 0;
        boolean success = true;

        // How many dices are rolled each time
        int tries = 1 + Math.abs(WildMagicHelper.getWildMagicLuck(player));
        // is adv. ore disadv.
        boolean adv = WildMagicHelper.getWildMagicLuck(player) >= 0;
        // base chance for this item
        int chance = CommonConfig.SECOND_CHANCE.get();

        while(success){
            if(adv){
                success = random.nextInt(100) < (1-Math.pow(1-chance/100.0f, tries))*100;
            } else {
                success = random.nextInt(100) < Math.pow(chance/100.0f, tries)*100;
            }

            if(success){
                secondChances++;
            }
        }

        return Math.min(secondChances, 10);
    }

    public enum Type{
        PROTECTION,
        ATTACK,
        POTION
    }

    public static void eventSecondDamage(LivingHurtEvent event){
        if(event.getSource().getEntity() instanceof Player attacker && !attacker.level().isClientSide()) {
            if (((SecondChanceItem) ItemInit.SECOND_ATTACK_RING.get()).isEquippedAndHasMana(attacker, 1.0F, true)) {
                int level = secondChances(attacker);
                if(level > 0) {
                    event.setAmount(event.getAmount() * (1+level));
                }
            }
        }
    }

    public static boolean eventSecondArmor(LivingHurtEvent event){
        if(event.getEntity() instanceof Player defender && !defender.level().isClientSide()) {
            if (((SecondChanceItem) ItemInit.SECOND_PROTECTION_RING.get()).isEquippedAndHasMana(defender, 4.0F, true)) {
                if(secondChances(defender) > 0){
                    event.setCanceled(true);
                    event.setAmount(0);
                    return true;
                }
            }
        }

        return false;
    }

    public static void eventSecondDrop(MobEffectEvent.Applicable event){
        MobEffectInstance original = event.getEffectInstance();

        if(original != null && event.getEntity() instanceof Player user && original.getEffect().isBeneficial() && !user.level().isClientSide()) {
            if (((SecondChanceItem) ItemInit.SECOND_DROP_RING.get()).isEquippedAndHasMana(user, 5.0F, true)) {
                int level = secondChances(user);
                if(level > 0){

                    int newAmplifier = original.getAmplifier() + level;

                    event.getEffectInstance().update(new MobEffectInstance(
                            event.getEffectInstance().getEffect(),
                            original.getDuration(),
                            newAmplifier,
                            event.getEffectInstance().isAmbient(),
                            event.getEffectInstance().isVisible(),
                            event.getEffectInstance().showIcon()
                    ));
                }
            }
        }
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

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if(this.type == Type.PROTECTION){
            tooltip.add(Component.translatable("item.fnc.second_protection_ring.description").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        } else if(this.type == Type.ATTACK){
            tooltip.add(Component.translatable("item.fnc.second_attack_ring.description").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        } else if(this.type == Type.POTION){
            tooltip.add(Component.translatable("item.fnc.second_drop_ring.description").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        }
        tooltip.add(Component.literal("  "));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}

