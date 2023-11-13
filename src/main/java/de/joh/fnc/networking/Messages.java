package de.joh.fnc.networking;

import de.joh.fnc.networking.packet.IncrementSelectedSpellAdjustmentC2SPacket;
import de.joh.fnc.networking.packet.IncrementSelectedWildMagicC2SPacket;
import de.joh.fnc.common.utils.RLoc;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

/**
 * Communication between client and server with packages of this mod.
 * @author Joh0210
 */
public class Messages {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;

    private static int id(){
        return packetId++;
    }

    public static void register(){
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(RLoc.create("messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(IncrementSelectedWildMagicC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(IncrementSelectedWildMagicC2SPacket::new)
                .encoder((IncrementSelectedWildMagicC2SPacket::toBytes))
                .consumer(IncrementSelectedWildMagicC2SPacket::handle)
                .add();

        net.messageBuilder(IncrementSelectedSpellAdjustmentC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(IncrementSelectedSpellAdjustmentC2SPacket::new)
                .encoder((IncrementSelectedSpellAdjustmentC2SPacket::toBytes))
                .consumer(IncrementSelectedSpellAdjustmentC2SPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(()->player), message);
    }
}
