package de.joh.fnc.common.item;

import com.mna.api.faction.IFaction;
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
import net.minecraftforge.event.entity.living.MobEffectEvent;
import org.jetbrains.annotations.NotNull;
import com.mna.api.items.IFactionSpecific;
import com.mna.api.items.TieredItem;
import top.theillusivec4.curios.api.CuriosApi;


import java.util.List;

public class TimeBeltItem extends TieredItem implements IFactionSpecific {
    public TimeBeltItem() {
        super((new Properties()).setNoRepair());
    }

    @Override
    public IFaction getFaction() {
        return FactionInit.PALADIN;
    }

    public static void eventHeavyWeapon(MobEffectEvent.Applicable event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide()) return;

        MobEffectInstance original = event.getEffectInstance();

        if (!original.getEffect().isBeneficial()) return;
        if (CuriosApi.getCuriosHelper().findCurios(entity, ItemInit.TIME_BELT.get()).isEmpty()) return;
        if (entity instanceof Player player) ((TimeBeltItem) ItemInit.TIME_BELT.get()).usedByPlayer(player);

        event.getEffectInstance().update(new MobEffectInstance(
                event.getEffectInstance().getEffect(),
                original.getDuration() * 2,
                original.getAmplifier(),
                event.getEffectInstance().isAmbient(),
                event.getEffectInstance().isVisible(),
                event.getEffectInstance().showIcon()
        ));
    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(Component.translatable("item.fnc.time_belt.description").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("  "));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
