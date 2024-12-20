package com.cyber.speed.components;

import com.cyber.speed.enums.ConditionEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cyber.speed.enums.ConditionEnum.LINEAR_SYMBOLS;
import static com.cyber.speed.enums.ConditionEnum.SAME_SYMBOLS;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScratchGameConfig {
   private int columns;
   private int rows;
   @JsonProperty("symbols")
   private Map<String, SymbolInfo> symbols;
   @JsonProperty("probabilities")
   private SymbolWrapper probabilities;
   @JsonProperty("win_combinations")
   private WinCombinations winCombinations;

   public Map<ConditionEnum, List<WinCondition>> getUpWinningCombinations(){
      Map<ConditionEnum, List<WinCondition>> combinations = new EnumMap<>(ConditionEnum.class);
      combinations.put(SAME_SYMBOLS,
              List.of(
                      this.getWinCombinations().getSameSymbol3Times(),
                      this.getWinCombinations().getSameSymbol4Times(),
                      this.getWinCombinations().getSameSymbol5Times(),
                      this.getWinCombinations().getSameSymbol6Times(),
                      this.getWinCombinations().getSameSymbol7Times(),
                      this.getWinCombinations().getSameSymbol8Times(),
                      this.getWinCombinations().getSameSymbol9Times()
              )
      );
      combinations.put(LINEAR_SYMBOLS,
              List.of(
                      this.getWinCombinations().getSameSymbolsHorizontally() ,
                      this.getWinCombinations().getSameSymbolsVertically(),
                      this.getWinCombinations().getSameSymbolsDiagonallyLeftToRight(),
                      this.getWinCombinations().getSameSymbolsDiagonallyRightToLeft()
              )
      );
      return combinations;
   }
}
