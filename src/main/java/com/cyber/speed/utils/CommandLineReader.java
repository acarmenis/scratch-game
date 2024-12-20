package com.cyber.speed.utils;

import com.cyber.speed.components.ScratchGameConfig;
import com.cyber.speed.evaulations.RewardResult;
import com.cyber.speed.exceptions.ScratchGameException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class CommandLineReader {

   private CommandLineReader() {}

   public static void readFromConsole(String[] args){
      // Step 1: Parse command-line arguments
      Map<String, String> arguments = parseArguments(args);
      arguments.forEach((k, v) -> {
         System.out.println(k + ": " + v);
      });

      // Step 2: Extract required parameters
      String configFilePath = arguments.get("--config");
      String bettingAmountStr = arguments.get("--betting-amount");

      if (Objects.isNull(configFilePath) || Objects.isNull(bettingAmountStr)) {
         throw new ScratchGameException("Usage: java -jar <your-jar-file> --config <config.json> --betting-amount <amount>");
      }
      //java -jar target/scratch-game-1.0-SNAPSHOT.jar --config src/main/resources/config.json --betting-amount 100
      try {
         int bettingAmount = Integer.parseInt(bettingAmountStr);
         System.out.println();
         System.out.println("Betting Amount: " + bettingAmount);
         ScratchGameConfig config = GameConfigReader.readGameConfig(configFilePath);
         MatrixGenerator matrixGenerator = new MatrixGenerator(config);
         String[][] matrix = matrixGenerator.generateMatrix();
         Map<String, Integer> symbolFrequencies = SymbolFrequency.calculateFrequencies(matrix);
         RewardCalculator calculator = new RewardCalculator(config,symbolFrequencies);
         RewardResult rewardResult = calculator.calculateRewards(matrix,bettingAmount);
         ResultPrinter.printResult(matrix, rewardResult);
         System.out.println();
      } catch (Exception e) {
         System.err.println("Error processing configuration or arguments: " + e.getMessage());
      }

   }

   /**
    * Parses the command-line arguments into a key-value map.
    */
   private static Map<String, String> parseArguments(String[] args) {
      Map<String, String> arguments = new HashMap<>();
      for (int i = 0; i < args.length - 1; i++) {
         if (args[i].startsWith("--")) {
            arguments.put(args[i], args[i + 1]);
         }
      }
      return arguments;
   }

}
