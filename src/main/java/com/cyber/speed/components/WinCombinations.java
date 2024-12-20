package com.cyber.speed.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WinCombinations {
   @JsonProperty("same_symbol_3_times")
   private WinCondition sameSymbol3Times;
   @JsonProperty("same_symbol_4_times")
   private WinCondition sameSymbol4Times;
   @JsonProperty("same_symbol_5_times")
   private WinCondition sameSymbol5Times;
   @JsonProperty("same_symbol_6_times")
   private WinCondition sameSymbol6Times;
   @JsonProperty("same_symbol_7_times")
   private WinCondition sameSymbol7Times;
   @JsonProperty("same_symbol_8_times")
   private WinCondition sameSymbol8Times;
   @JsonProperty("same_symbol_9_times")
   private WinCondition sameSymbol9Times;
   @JsonProperty("same_symbols_horizontally")
   private LinearWinCondition sameSymbolsHorizontally;
   @JsonProperty("same_symbols_vertically")
   private LinearWinCondition sameSymbolsVertically;
   @JsonProperty("same_symbols_diagonally_left_to_right")
   private LinearWinCondition sameSymbolsDiagonallyLeftToRight;
   @JsonProperty("same_symbols_diagonally_right_to_left")
   private LinearWinCondition sameSymbolsDiagonallyRightToLeft;
}
