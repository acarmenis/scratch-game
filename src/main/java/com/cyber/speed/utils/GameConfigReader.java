package com.cyber.speed.utils;


import com.cyber.speed.components.ScratchGameConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class GameConfigReader {

   static Logger logger = Logger.getLogger(GameConfigReader.class.getName());

   // private static final String GAME_CONFIG_FILE_PATH = "src/main/resources/config.json";

   private GameConfigReader(){}

   public static ScratchGameConfig readGameConfig(final String filePath) {
      ScratchGameConfig scratchGameConfig = null;
      try {
         // Step 1: Initialize ObjectMapper
         ObjectMapper objectMapper = new ObjectMapper();
         // Read JSON file
         scratchGameConfig = objectMapper.readValue(new File(filePath), ScratchGameConfig.class);
         // Print parsed object (for verification)
      } catch (Exception e) {
         logger.log(Level.SEVERE, "Error reading game config", e);
      }
      return scratchGameConfig;
   }

}
