package de.joh.fnc.common.event;

import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.common.capability.SmitePlayerCapability;
import de.joh.fnc.common.capability.PlayerCapabilityProvider;
import de.joh.fnc.common.util.RLoc;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Event handlers for registering and handling all {@link PlayerCapabilityProvider PlayerCapabilities}
 * @author Joh0210
 */
@Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityForgeEventHandler {
    public CapabilityForgeEventHandler() {
    }

    @SubscribeEvent
    public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(RLoc.create("dnc_player_data"), new PlayerCapabilityProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        Player player = event.getEntity();
        Player original = event.getOriginal();
        original.reviveCaps();
        player.getCapability(PlayerCapabilityProvider.PLAYER_SMITE).ifPresent(
                (newSmite) -> original.getCapability(PlayerCapabilityProvider.PLAYER_SMITE).ifPresent(
                        originalSmite -> newSmite.copyFrom(originalSmite, event.isWasDeath())
                ));
        event.getOriginal().invalidateCaps();
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(SmitePlayerCapability.class);
    }
}
