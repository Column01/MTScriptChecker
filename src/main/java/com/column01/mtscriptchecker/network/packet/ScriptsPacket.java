package com.column01.mtscriptchecker.network.packet;

import com.column01.mtscriptchecker.util.ConfigHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TextComponentTranslation;
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
        scripts = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBytes(scripts.getBytes());
    }

    public static class Handler implements IMessageHandler<ScriptsPacket, IMessage> {
        @Override
        public IMessage onMessage(ScriptsPacket message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            player.server.addScheduledTask(() -> {
                String clientScriptsHash = ConfigHandler.getHashString();
                String serverScriptsHash = message.scripts;
                if(!clientScriptsHash.equals(serverScriptsHash)) {
                    ITextComponent alert = new TextComponentTranslation(TextFormatting.BOLD + "You do not have all scripts that are present on the server! This may result in missing recipes or tooltips. Please contact a server administrator if you believe this is an error.");
                    player.sendMessage(alert);
                }
            });
            return null;
        }
    }
}
