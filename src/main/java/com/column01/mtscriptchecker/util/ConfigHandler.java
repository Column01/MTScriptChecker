package com.column01.mtscriptchecker.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import com.column01.mtscriptchecker.MTScriptChecker;
import com.column01.mtscriptchecker.objects.HashObject;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.Type;

public class ConfigHandler {
    public static Logger logger = MTScriptChecker.getLogger();

    public static void init() {
        ConfigManager.sync(MTScriptChecker.MODID, Type.INSTANCE);
    }

    // Returns the hash object stored in hash.json. If there is an error, it will return and empty string.
    public static String getHashString() {
        Gson gson = new Gson();
        try {
            File file = new File("config/MTScriptChecker/");
            if(!file.exists()) {
                file.mkdirs();
            }
            HashObject hashObject = gson.fromJson(new FileReader("config/MTScriptChecker/hash.json"), HashObject.class);
            return hashObject.getHash();
        } catch (Exception e) {
            logger.error("Error reading scripts hash from storage file.");
            logger.error(ExceptionUtils.getStackTrace(e));
            return "";
        }
    }

    // Writes the hash to the json file.
    public static void writeHashString(String hash) {
        logger.info("Attempting to write hash to hash file.");
        Gson gson = new Gson();
        HashObject hashObject = new HashObject(hash);
        try {
            File file = new File("config/MTScriptChecker/");
            if(!file.exists()) {
                file.mkdirs();
            }
            FileWriter writer = new FileWriter("config/MTScriptChecker/hash.json");
            gson.toJson(hashObject, writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            logger.error("Error writing scripts hash to storage file.");
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }
    
    // Not used. This is the config annotation stuff that I might use later on in development.
    @Config(modid = MTScriptChecker.MODID, name = "MTScriptChecker", type = Type.INSTANCE)
    public static class CONFIG_SETTINGS {
        @Name("Hashed Scripts Value")
        public static String hash = "";
    }
}