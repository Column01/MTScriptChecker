package com.github.column01.mtscriptchecker.network.packet;

import com.github.column01.mtscriptchecker.MTScriptChecker;
import com.github.column01.mtscriptchecker.util.ConfigHandler;

import org.apache.logging.log4j.Logger;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ScriptsPacket implements IMessage {

    public ScriptsPacket(){}

    private String scripts;

    public ScriptsPacket(String scripts) {
        this.scripts = scripts;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        Logger logger = MTScriptChecker.getLogger();
        logger.info("Converting byte buffer to a string.");
        scripts = ByteBufUtils.readUTF8String(buf);
        logger.info(scripts);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        Logger logger = MTScriptChecker.getLogger();
        logger.info("Converting scripts to bytes to send packet.");
        ByteBufUtils.writeUTF8String(buf, scripts);
    }

    public static class Handler implements IMessageHandler<ScriptsPacket, IMessage> {
        @Override
        public IMessage onMessage(ScriptsPacket message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            player.server.addScheduledTask(() -> {
                Logger logger = MTScriptChecker.getLogger();
                logger.info("Recieved scripts packet from the client: " + player.getName());
                String serverScriptsHash = ConfigHandler.getHashString();
                String clientScriptsHash = message.scripts;
                logger.info("Client script hash from packet: " + clientScriptsHash);
                logger.info("Server script hash from file: " + serverScriptsHash);
                if(!clientScriptsHash.equals(serverScriptsHash)) {
                    logger.info("Scripts do not match");
                    ITextComponent alert = new TextComponentString(TextFormatting.BOLD + "You do not have all scripts that are present on the server! This may result in missing recipes or tooltips. Please contact a server administrator if you believe this is an error.");
                    player.sendMessage(alert);
                } else {
                    logger.info("Scripts match");
                }
            });
            return null;
        }
    }
}
