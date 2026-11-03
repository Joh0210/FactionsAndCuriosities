package de.joh.fnc.common.item;

import com.mna.api.faction.IFaction;
import com.mna.items.artifice.curio.IPreEnchantedItem;
import de.joh.fnc.common.init.EffectInit;
import de.joh.fnc.common.init.FactionInit;
import de.joh.fnc.common.init.ItemInit;
import de.joh.fnc.common.util.CommonConfig;
import de.joh.tnl.api.item.ChargeableDragonMagicItem;
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
 * <br>Dragon Magic: Raises the maximum number of Frenzy Stacks by {@link CommonConfig#FRENZY_RING_DRAGON_MAGIC_MAX_LEVEL_BONUS}.
 * @author Joh0210
 */
public class FrenzyItem extends ChargeableDragonMagicItem implements IPreEnchantedItem<FrenzyItem> {
        private final boolean major;

        public FrenzyItem(boolean major) {
                super((new Properties()).setNoRepair(), major ? 1000.0F : 250.0F);
                this.major = major;
        }

        @Override
        public String dragonMagicID() {
                return "item.fnc.frenzy_ring.dragonmagic";
        }

        /**
         * @param wearer Wearer of the ring
         * @return The highest amplifier (= max Frenzy Stacks - 1) the ring can build up for this wearer, raised by {@link CommonConfig#FRENZY_RING_DRAGON_MAGIC_MAX_LEVEL_BONUS} with Dragon Magic.
         */
        private int getMaxAmplifier(LivingEntity wearer) {
                return (major ? 9 : 4) + (this.hasDragonMagic(wearer) ? CommonConfig.FRENZY_RING_DRAGON_MAGIC_MAX_LEVEL_BONUS.get() : 0);
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
                int maxAmplifier = usedRing.getMaxAmplifier(attacker);

                MobEffectInstance current = attacker.getEffect(EffectInit.FRENZY.get());

                if (current != null && current.getAmplifier() > maxAmplifier) return;

                int amplifier = 0;

                if (current != null) {
                        amplifier = Math.min(maxAmplifier, current.getAmplifier() + 1);
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
