package com.github.column01.mtscriptchecker.events;

import com.github.column01.mtscriptchecker.MTScriptChecker;
import com.github.column01.mtscriptchecker.network.packet.PacketSender;
import com.github.column01.mtscriptchecker.network.packet.ScriptsPacket;
import com.github.column01.mtscriptchecker.util.ConfigHandler;

import org.apache.logging.log4j.Logger;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandlers {
    @SubscribeEvent
    public void playerLogin(EntityJoinWorldEvent event) {
        Logger logger = MTScriptChecker.getLogger();
        if(event.getWorld().isRemote) {
            if(event.getEntity() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.getEntity();
                logger.info("Player logged in. " + player.getName());
                String scriptsHash = ConfigHandler.getHashString();
                if (!scriptsHash.isEmpty()) {
                    logger.info("Script hash is not empty. " + scriptsHash);
                    ScriptsPacket msg = new ScriptsPacket(scriptsHash);
                    PacketSender.sendToServer(msg);
                }
            }
        }
    }
}