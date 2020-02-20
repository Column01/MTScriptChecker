package com.github.column01.mtscriptchecker.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import com.github.column01.mtscriptchecker.MTScriptChecker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class ScriptHashing {
    public static final File SCRIPT_DIR = new File("scripts");
    public static final String HEXES = "0123456789ABCDEF";
    public static Logger logger;

    public static void init() throws Exception {
        logger = LogManager.getLogger(MTScriptChecker.MODID);
        Side s = FMLCommonHandler.instance().getEffectiveSide();

        if(s == Side.CLIENT) {
            // Collect a list of script files
            List<File> scripts = getScriptFiles();
            List<String> hashes = new ArrayList<String>();
            // If it is not an empty list, generate a list of hashes for each file in the scripts list
            if(!scripts.isEmpty()){
                for(File f: scripts) {
                    String hash = getHex(createChecksum(f));
                    hashes.add(hash);
                }
                // Get the final script hash by hashing the list of hashes and then getting the hex interpretation of that.
                // Then write the hash to file.
                String finalScriptHash = getHex(createListChecksum(hashes));
                logger.info("Calculated Scripts hash: " + finalScriptHash);
                ConfigHandler.writeHashString(finalScriptHash);
            } else {
                // MD5 hash of the MODID so the hash.json file is created regardless.
                ConfigHandler.writeHashString("D8ED1479D5F8D5305C5471B9737BBEBE");
            }
        }

        if(s == Side.SERVER) {
            // Collect a list of script files
            List<File> scripts = getScriptFiles();
            List<String> hashes = new ArrayList<String>();
            // If it is not an empty list, generate a list of hashes for each file in the scripts list
            if(!scripts.isEmpty()){
                for(File f: scripts) {
                    String hash = getHex(createChecksum(f));
                    hashes.add(hash);
                }
                // Get the final script hash by hashing the list of hashes and then getting the hex interpretation of that.
                // Then write the hash to file.
                String finalScriptHash = getHex(createListChecksum(hashes));
                logger.info("Calculated Scripts hash: " + finalScriptHash);
                ConfigHandler.writeHashString(finalScriptHash);
            } else {
                // MD5 hash of the MODID so the hash.json file is created regardless.
                ConfigHandler.writeHashString("D8ED1479D5F8D5305C5471B9737BBEBE");
            }
        }
    }

    // Calculates a checksum for the given file
    public static byte[] createChecksum(File file) throws Exception {
        InputStream fis =  new FileInputStream(file);
        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;
        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);
        fis.close();
        return complete.digest();
    }

    // Calculates a checksum for the given string list
    public static byte[] createListChecksum(List<String> strings) throws Exception {
        // Convert the list of strings to a byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for(String str: strings){
            baos.write(str.getBytes());
        }
        // Turn that into an input stream and do the checksum
        InputStream fis =  new ByteArrayInputStream(baos.toByteArray());
        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;
        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);
        fis.close();
        return complete.digest();
    }

    
    // Returns the hex representation of a byte array
    public static String getHex( byte[] raw ) {
        if ( raw == null ) {
            return null;
        }
        final StringBuilder hex = new StringBuilder( 2 * raw.length );
        for ( final byte b : raw ) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4))
                .append(HEXES.charAt((b & 0x0F)));
        }
        return hex.toString();
    }

    // Methods below this line were borrowed from CraftTweaker's API for simplicity's sake. 
    // This is allowed under the MIT License for CT here: https://github.com/CraftTweaker/CraftTweaker/blob/1.12/LICENSE
    public static void findScriptFiles(File path, List<File> files) {
        if(path.isDirectory()) {
            for(File file : path.listFiles()) {
                if(file.isDirectory()) {
                    findScriptFiles(file, files);
                } else {
                    if(file.getName().toLowerCase().endsWith(".zs")) {
                        files.add(file);
                    }
                }
            }
        }
    }

    public static List<File> getScriptFiles() {
        List<File> fileList = new ArrayList<>();
        findScriptFiles(SCRIPT_DIR, fileList);
        return fileList;
    }
}