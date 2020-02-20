package com.column01.mtscriptchecker.events;

import com.column01.mtscriptchecker.network.packet.PacketSender;
import com.column01.mtscriptchecker.network.packet.ScriptsPacket;
import com.column01.mtscriptchecker.util.ConfigHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PlayerLoginEventHandler {
    @SubscribeEvent
    public static void playerLogin(PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;
        String scriptsHash = ConfigHandler.getHashString();
        if (scriptsHash.length() > 0) {
            IMessage msg = new ScriptsPacket(scriptsHash);
            PacketSender.sendTo((EntityPlayerMP) player, msg);
        }
    }
}