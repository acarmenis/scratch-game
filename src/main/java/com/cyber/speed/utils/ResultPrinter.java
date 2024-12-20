package com.cyber.speed.utils;

import com.cyber.speed.evaulations.RewardResult;

import java.util.Arrays;

public final class ResultPrinter {

   private ResultPrinter(){}

   public static void printResult(String[][] matrix, RewardResult result) {
      System.out.println();
      System.out.println("Matrix:");
      System.out.println("===============================");
      for (String[] row : matrix) {
         System.out.println("\t"+Arrays.toString(row));
      }
      System.out.println();
      System.out.println("Reward: " + result.reward);
      System.out.println("===============================");
      System.out.println();
      System.out.println("Applied Winning Combinations:" );
      System.out.println("===============================");
      result.appliedCombinations.forEach((k,v)-> System.out.println("\t"+k + " -> " + v));
      System.out.println();
      System.out.println("Applied Bonus Symbol: " + result.appliedBonusSymbols);
      System.out.println();
   }
}
