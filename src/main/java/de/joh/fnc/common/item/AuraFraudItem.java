package de.joh.fnc.common.item;

import com.mna.api.events.AuraEvent;
import com.mna.api.faction.IFaction;
import com.mna.api.items.IFactionSpecific;
import com.mna.api.items.TieredItem;
import de.joh.fnc.common.init.FactionInit;
import de.joh.fnc.common.init.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

public class AuraFraudItem extends TieredItem implements IFactionSpecific {
    public AuraFraudItem() {
        super((new Properties()).setNoRepair().stacksTo(1));
    }

    @Override
    public IFaction getFaction() {
        return FactionInit.PALADIN;
    }

    public static void eventAuraFraud(AuraEvent.Numerics event) {
        Player player = event.getSource();
//        if (player.level().isClientSide()) return;
        if (CuriosApi.getCuriosHelper().findCurios(player, ItemInit.AURA_FRAUD_RING.get()).isEmpty()) return;
        event.setManaCost(0);

    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(Component.translatable("item.fnc.aura_fraud_ring.description").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("  "));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}