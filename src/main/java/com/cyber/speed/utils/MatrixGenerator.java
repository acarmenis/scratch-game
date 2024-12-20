package com.cyber.speed.utils;

import com.cyber.speed.components.ScratchGameConfig;
import com.cyber.speed.components.StandardSymbol;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class MatrixGenerator {

   // Instance variables
   private final ScratchGameConfig config;          // Configuration for rows, columns, and probabilities
   private final Random random = new Random();     // Random generator for symbol selection

   /**
    * Constructor to initialize the generator with configuration.
    *
    * @param config Configuration containing rows, columns, and symbol probabilities
    */
   public MatrixGenerator(ScratchGameConfig config) {
      this.config = config;
   }

   /**
    * Generates a game matrix filled with standard symbols and bonus symbols.
    *
    * @return A 2D matrix of symbols as Strings.
    */
   public String[][] generateMatrix() {
      String[][] matrix = new String[config.getRows()][config.getColumns()]; // Initialize the game matrix

      // Step 1: Fill the matrix with standard symbols based on probabilities
      for (int row = 0; row < config.getRows(); row++) {
         for (int col = 0; col < config.getColumns(); col++) {
            matrix[row][col] = getRandomSymbol(config.getProbabilities().getStandardSymbols(), row, col);
         }
      }

      // Step 2: Randomly place bonus symbols in the matrix
      placeBonusSymbols(matrix);

      return matrix;
   }

   /**
    * Retrieves a random symbol for the specified grid location (row, column)
    * based on the provided list of probability grids.
    *
    * @param grids List of StandardSymbol objects
    * @param row   Current row index
    * @param col   Current column index
    * @return A randomly selected symbol as a String
    */
   private String getRandomSymbol(List<StandardSymbol> grids, int row, int col) {
      // Find the grid that matches the current row and column
      StandardSymbol grid = grids.stream()
              .filter(g -> g.getRow() == row && g.getColumn() == col) // Match grid by row and column
              .findFirst()
              .orElse(grids.get(0)); // Fall back to the first grid if no match is found

      // Get a random symbol from the grid's symbol probabilities
      return getRandomSymbolFromMap(grid.getSymbols());
   }

   /**
    * Selects a random symbol based on weighted probabilities from a map.
    *
    * @param symbolProbabilities Map containing symbols and their associated weights
    * @return A randomly selected symbol as a String
    */
   private String getRandomSymbolFromMap(Map<String, Integer> symbolProbabilities) {
      int totalWeight = symbolProbabilities.values().stream()
              .mapToInt(i -> i)
              .sum(); // Calculate the total weight of all symbols

      // generates a random integer starting from [0 (inclusive) up to totalWeight], totalWeight ~ 21
      int randomValue = random.nextInt(totalWeight); // Generate a random value within the weight range

      // Traverse the map and deduct weights to find the selected symbol
      for (Map.Entry<String, Integer> entry : symbolProbabilities.entrySet()) {
         randomValue -= entry.getValue(); // symbol's weight
         if (randomValue < 0) {
            return entry.getKey(); // Return the symbol when the value falls below zero
         }
      }
      return null; // Should not reach here under normal conditions
   }

   /**
    * Randomly places bonus symbols into the matrix with a fixed probability (10%).
    *
    * @param matrix The game matrix to be updated
    */
   private void placeBonusSymbols(String[][] matrix) {
      for (int row = 0; row < config.getRows(); row++) {
         for (int col = 0; col < config.getColumns(); col++) {
            //  Randomly decide whether to place a bonus symbol (10% chance)
            //  It generates a pseudorandom double value uniformly distributed in the range: [0.0, 1.0)
            if (random.nextDouble() < 0.1) {
               matrix[row][col] = getRandomSymbolFromMap(config.getProbabilities().getBonusSymbols().getSymbols());
            }
         }
      }
   }



}
