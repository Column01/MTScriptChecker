package com.github.column01.mtscriptchecker;

import com.github.column01.mtscriptchecker.events.ConfigChangedEventHandler;
import com.github.column01.mtscriptchecker.events.PlayerLoginEventHandler;
import com.github.column01.mtscriptchecker.network.packet.PacketSender;
import com.github.column01.mtscriptchecker.util.ConfigHandler;
import com.github.column01.mtscriptchecker.util.ScriptHashing;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = MTScriptChecker.MODID, name = MTScriptChecker.NAME, version = MTScriptChecker.VERSION, dependencies = "after:crafttweaker")
public class MTScriptChecker {
    public static final String MODID = "mtscheck";
    public static final String NAME = "MTScriptChecker";
    public static final String VERSION = "1.0-SNAPSHOT";
    public static Logger logger;

    @Instance
    public static MTScriptChecker instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = LogManager.getLogger(MODID);
        logger.info(NAME + "is loading.");
        logger.info("Subscribing to the player login event.");
        MinecraftForge.EVENT_BUS.register(new PlayerLoginEventHandler());
        MinecraftForge.EVENT_BUS.register(new ConfigChangedEventHandler());
        logger.info("Initializing packet sender and config");
        PacketSender.init();
        ConfigHandler.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        try {
            ScriptHashing.init();
            logger.info("Scripts have been hashed and written to the hash.json file");
        } catch (Exception e) {
            logger.error("There was an error when collecting a hash of the scripts files. Make sure you have craft tweaker installed to use this mod!");
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }

    public static Logger getLogger(){
        return logger;
    }
}
