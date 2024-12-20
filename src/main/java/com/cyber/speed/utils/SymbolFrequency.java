package com.cyber.speed.utils;

import java.util.HashMap;
import java.util.Map;

public final class SymbolFrequency {

   private SymbolFrequency() {}

   public static Map<String, Integer> calculateFrequencies(String[][] array) {
      Map<String, Integer> frequencyMap = new HashMap<>();
      // Iterate through the 2D array
      for (String[] row : array) {
         for (String symbol : row) {
            // Use computeIfAbsent to initialize the count and then increment
            frequencyMap.putIfAbsent(symbol, 0); // Initialize if absent
            frequencyMap.put(symbol, frequencyMap.get(symbol) + 1); // Increment
         }
      }
      return frequencyMap;
   }

}
