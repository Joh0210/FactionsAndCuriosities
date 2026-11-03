package de.joh.fnc.common.item;

import com.mna.api.faction.IFaction;
import com.mna.items.artifice.curio.IPreEnchantedItem;
import de.joh.fnc.api.wildmagic.WildMagicHelper;
import de.joh.fnc.common.init.FactionInit;
import de.joh.fnc.common.init.ItemInit;
import de.joh.fnc.common.util.CommonConfig;
import de.joh.tnl.api.item.ChargeableDragonMagicItem;
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
 * <br>Dragon Magic: Every roll gets an additional, independent chance of {@link CommonConfig#SECOND_CHANCE_DRAGON_MAGIC}.
 * Since the roll only fails if both chances fail, the chance of a roll becomes
 * {@code 100 - (100 - SECOND_CHANCE_DRAGON_MAGIC) * (100 - SECOND_CHANCE) / 100} (%).
 * @author Joh0210
 */
public class SecondChanceItem extends ChargeableDragonMagicItem implements IPreEnchantedItem<SecondChanceItem> {
    public final SecondChanceItem.Type type;

    public SecondChanceItem(SecondChanceItem.Type type) {
        super((new Properties()).setNoRepair().rarity(Rarity.RARE).stacksTo(1), 500.0F);
        this.type = type;
    }

    @Override
    public String dragonMagicID() {
        return "item.fnc.second_drop_ring.dragonmagic";
    }

    /**
     * @param player Wearer of the ring
     * @return Chance (in %) that a single roll of this ring succeeds, before the Wild Magic Luck of the player is applied
     */
    public double getChance(Player player) {
        double chance = CommonConfig.SECOND_CHANCE.get();

        if (this.hasDragonMagic(player)) {
            chance = 100 - (100 - CommonConfig.SECOND_CHANCE_DRAGON_MAGIC.get()) * (100 - chance) / 100;
        }

        return chance;
    }

    /**
     * @param player Wearer of the ring
     * @return How many Second Chances in a row the player gets (max. 10)
     */
    public int secondChances(Player player) {
        Random random = new Random();

        int secondChances = 0;
        boolean success = true;

        // How many dices are rolled each time
        int tries = 1 + Math.abs(WildMagicHelper.getWildMagicLuck(player));
        // is adv. ore disadv.
        boolean adv = WildMagicHelper.getWildMagicLuck(player) >= 0;
        // base chance for this item
        double chance = this.getChance(player) / 100.0;

        while(success){
            if(adv){
                success = random.nextDouble() < 1 - Math.pow(1 - chance, tries);
            } else {
                success = random.nextDouble() < Math.pow(chance, tries);
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
            SecondChanceItem ring = (SecondChanceItem) ItemInit.SECOND_ATTACK_RING.get();
            if (ring.isEquippedAndHasMana(attacker, 1.0F, true)) {
                int level = ring.secondChances(attacker);
                if(level > 0) {
                    event.setAmount(event.getAmount() * (1+level));
                }
            }
        }
    }

    public static boolean eventSecondArmor(LivingHurtEvent event){
        if(event.getEntity() instanceof Player defender && !defender.level().isClientSide()) {
            SecondChanceItem ring = (SecondChanceItem) ItemInit.SECOND_PROTECTION_RING.get();
            if (ring.isEquippedAndHasMana(defender, 4.0F, true)) {
                if(ring.secondChances(defender) > 0){
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
            SecondChanceItem ring = (SecondChanceItem) ItemInit.SECOND_DROP_RING.get();
            if (ring.isEquippedAndHasMana(user, 5.0F, true)) {
                int level = ring.secondChances(user);
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

