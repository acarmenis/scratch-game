package com.cyber.speed.enums;

import com.cyber.speed.exceptions.ScratchGameException;
import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Getter
public enum SymbolEnum {
   A("A", 1, "standard", null),
   B("B", 2, "standard", null),
   C("C", 3, "standard", null),
   D("D", 4, "standard", null),
   E("E", 5, "standard", null),
   F("F", 6, "standard", null),
   TEN_X("10x", 1, "bonus", "multiply_reward"),
   FIVE_X("5x", 2, "bonus", "multiply_reward"),
   PLUS_1000_X("+1000", 3, "bonus", "extra_bonus"),
   PLUS_500_X("+500", 4, "bonus", "extra_bonus"),
   MISS("MISS", 5, "bonus", "miss");
   /**
    * Private field to store the associated value for each enum constant
    */
   private final int index;
   private final String symbol;
   private final String type;
   private final String impact;
   /**
    *
    * @param index The int index associated with the enum constant.
    */
   SymbolEnum(String symbol, int index, String type, String impact) {
      this.symbol = symbol;
      this.index = index;
      this.type = type;
      this.impact = impact;
   }

   /**
    *
    * @param symbol The symbol value to search for.
    * @return The matching enum constant, or IllegalArgumentException if no match is found.
    */
   public static SymbolEnum fromSymbol(String symbol) {
      return Arrays.stream(values())
              .filter(value -> Objects.equals(value.getSymbol(), symbol))
              .findFirst()
              .orElseThrow(() -> new ScratchGameException("No symbol found for symbol: " + symbol));
   }

   public static List<SymbolEnum> fromType(String type) {
      return Arrays.stream(values())
              .filter(value -> Objects.equals(value.getType(), type))
              .sorted(Comparator.comparingInt(SymbolEnum::getIndex)) // Sort by index asc
              .toList();
   }

   public static List<SymbolEnum> fromImpact(String impact) {
      return Arrays.stream(values())
              .filter(value -> Objects.equals(value.getImpact(), impact))
              .sorted(Comparator.comparingInt(SymbolEnum::getIndex)) // Sort by index asc
              .toList();
   }
}
