package de.joh.fnc.common.item;

import com.mna.api.faction.IFaction;
import com.mna.api.items.ChargeableItem;
import com.mna.items.artifice.curio.IPreEnchantedItem;
import de.joh.fnc.common.init.EffectInit;
import de.joh.fnc.common.init.FactionInit;
import de.joh.fnc.common.init.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * For each kill within one minute, you gain 1 Stack of Frenzy. Each Stack provides an 20% additional damage.
 * @author Joh0210
 */
public class FrenzyItem extends ChargeableItem implements IPreEnchantedItem<FrenzyItem> {
        private final boolean major;

        public FrenzyItem(boolean major) {
                super((new Properties()).setNoRepair(), major ? 1000.0F : 250.0F);
                this.major = major;
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

        public static void eventFrenzyKill(LivingDeathEvent event) {
                if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
                if (attacker.level().isClientSide()) return;

                FrenzyItem majorRing = (FrenzyItem) ItemInit.FRENZY_RING_MAJOR.get();
                FrenzyItem minorRing = (FrenzyItem) ItemInit.FRENZY_RING.get();

                boolean hasMajor = majorRing.isEquippedAndHasMana(attacker, 4.0F, false);
                boolean hasMinor = minorRing.isEquippedAndHasMana(attacker, 2.0f, false);

                if (!hasMajor && !hasMinor) return;

                FrenzyItem usedRing = hasMajor ? majorRing : minorRing;

                MobEffectInstance current = attacker.getEffect(EffectInit.FRENZY.get());

                if (current != null && current.getAmplifier() > (hasMajor ? 9 : 4)) return;

                int amplifier = 0;

                if (current != null) {
                        amplifier = Math.min(hasMajor ? 9 : 4, current.getAmplifier() + 1);
                } else {
                        // Mana nur beim ersten Stack verbrauchen
                        usedRing.isEquippedAndHasMana(attacker, hasMajor ? 4.0F : 2.0f, true);
                }

                attacker.addEffect(new MobEffectInstance(EffectInit.FRENZY.get(),hasMajor ? 1200 : 600, amplifier));
        }


        @OnlyIn(Dist.CLIENT)
        @Override
        public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
                tooltip.add(Component.translatable(major? "item.fnc.frenzy_ring_greater.description":"item.fnc.frenzy_ring.description").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
                tooltip.add(Component.literal("  "));
                super.appendHoverText(stack, world, tooltip, flag);
        }
}
