package de.joh.fnc.common.item;

import com.mna.api.faction.IFaction;
import com.mna.items.artifice.curio.IPreEnchantedItem;
import de.joh.fnc.common.event.DamageEventHandler;
import de.joh.fnc.common.init.FactionInit;
import de.joh.fnc.common.init.ItemInit;
import de.joh.fnc.common.util.CommonConfig;
import de.joh.tnl.api.item.ChargeableDragonMagicItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Increases the damage of your weapon attacks by 25% (Greater Ring: 50%), as long as you aren't wielding anything in your off-hand.
 * <br>Dragon Magic: Raises the bonus by another {@link CommonConfig#HEAVY_WEAPON_RING_DRAGON_MAGIC_DAMAGE_BONUS}%.
 * @see DamageEventHandler
 * @author Joh0210
 */
public class HeavyWeaponRingItem extends ChargeableDragonMagicItem implements IPreEnchantedItem<HeavyWeaponRingItem> {
    private final boolean major;

    public HeavyWeaponRingItem(boolean major) {
        super((new Properties()).setNoRepair(), major ? 2000.0F : 500.0F);
        this.major = major;
    }

    @Override
    public String dragonMagicID() {
        return "item.fnc.heavy_weapon_ring.dragonmagic";
    }

    protected boolean tickEffect(ItemStack stack, Player player, Level world, int slot, float mana, boolean selected) {
        return false;
    }

    protected boolean tickCurio() {
        return false;
    }

    @Override
    public IFaction getFaction() {
        return FactionInit.PALADIN;
    }

    public static void eventHeavyWeapon(LivingHurtEvent event) {
        if (!(event.getSource().getEntity() instanceof Player attacker)) return;
        if (attacker.level().isClientSide()) return;

        if(!(event.getSource().is(DamageTypes.MOB_ATTACK)
                | event.getSource().is(DamageTypes.PLAYER_ATTACK)
                | event.getSource().is(DamageTypes.ARROW)
            )) return;

        if(attacker.getItemBySlot(EquipmentSlot.OFFHAND) != ItemStack.EMPTY) return;

        HeavyWeaponRingItem majorRing = (HeavyWeaponRingItem) ItemInit.HEAVY_WEAPON_RING_GREATER.get();
        HeavyWeaponRingItem minorRing = (HeavyWeaponRingItem) ItemInit.HEAVY_WEAPON_RING.get();

        boolean hasMajor = false;
        if (majorRing.isEquippedAndHasMana(attacker, 2.0F, true)){
            hasMajor = true;
        } else if (!minorRing.isEquippedAndHasMana(attacker, 1.0f, true)) {
          return;
        }

        HeavyWeaponRingItem ring = hasMajor ? majorRing : minorRing;
        float multiplier = hasMajor ? 1.5f : 1.25f;
        if (ring.hasDragonMagic(attacker)) {
            multiplier += CommonConfig.HEAVY_WEAPON_RING_DRAGON_MAGIC_DAMAGE_BONUS.get() / 100.0f;
        }

        event.setAmount(event.getAmount() * multiplier);
    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(Component.translatable(major? "item.fnc.heavy_weapon_ring_greater.description":"item.fnc.heavy_weapon_ring.description").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("  "));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}