package de.joh.fnc.client.event;

import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.common.item.DebugOrbSpellAdjustmentItem;
import de.joh.fnc.common.item.DebugOrbWildMagicItem;
import de.joh.fnc.networking.Messages;
import de.joh.fnc.networking.packet.IncrementSelectedSpellAdjustmentC2SPacket;
import de.joh.fnc.networking.packet.IncrementSelectedWildMagicC2SPacket;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Handles for client specific events
 * @author Joh0210
 */
@Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEventHandler {
    private static Player getPlayer() {
        return FactionsAndCuriosities.instance.getClientPlayer();
    }

    /**
     * Event to change the selected Wild Magic of the Debug Rod by Scrolling while Shifting.
     * @see DebugOrbWildMagicItem
     */
    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollEvent event){
        if (getPlayer() != null
                && getPlayer().getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof DebugOrbWildMagicItem debugRod
                && getPlayer().isShiftKeyDown())
        {
            Messages.sendToServer(new IncrementSelectedWildMagicC2SPacket(event.getScrollDelta() < 0));
            debugRod.incrementWildMagicIterator(getPlayer().getItemBySlot(EquipmentSlot.MAINHAND), event.getScrollDelta() < 0, getPlayer());
            event.setCanceled(true);
        }

        if (getPlayer() != null
                && getPlayer().getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof DebugOrbSpellAdjustmentItem debugRod
                && getPlayer().isShiftKeyDown())
        {
            Messages.sendToServer(new IncrementSelectedSpellAdjustmentC2SPacket(event.getScrollDelta() < 0));
            debugRod.incrementSpellAdjustmentIterator(getPlayer().getItemBySlot(EquipmentSlot.MAINHAND), event.getScrollDelta() < 0, getPlayer());
            event.setCanceled(true);
        }
    }
}
