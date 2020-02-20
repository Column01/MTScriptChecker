package com.column01.mtscriptchecker.events;

import com.column01.mtscriptchecker.MTScriptChecker;
import com.column01.mtscriptchecker.util.ConfigHandler.CONFIG_SETTINGS;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigChangedEventHandler {
    public static Logger logger = MTScriptChecker.getLogger();
    @SubscribeEvent
    public void onConfigChangedEvent(OnConfigChangedEvent event) {
        logger.info("Config change event fired from: " + event.getClass().getName());
        if (event.getModID().equals(MTScriptChecker.MODID)) {
            logger.info("Config syncing");
            logger.info("Old value for hash in config: " + CONFIG_SETTINGS.hash);
            ConfigManager.sync(MTScriptChecker.MODID, Type.INSTANCE);
            logger.info("New value for hash after sync: " + CONFIG_SETTINGS.hash);
        }
    }
}