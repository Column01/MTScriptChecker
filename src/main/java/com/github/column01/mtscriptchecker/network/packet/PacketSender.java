package com.github.column01.mtscriptchecker.network.packet;

import com.github.column01.mtscriptchecker.MTScriptChecker;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketSender {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(MTScriptChecker.MODID);

    // Initialize the packet handlers
    public static void init() {
        int id = 0;
        INSTANCE.registerMessage(ScriptsPacket.Handler.class, ScriptsPacket.class, id++, Side.CLIENT);
    }

    // Send packet to player
    public static void sendTo(EntityPlayerMP player, IMessage msg) {
        INSTANCE.sendTo(msg, player);
    }

    // Send packet to server
    public static void sendToServer(IMessage msg) {
        INSTANCE.sendToServer(msg);
    }

    private PacketSender() {}
}
