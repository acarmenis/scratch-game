package com.cyber.speed.evaulations;

import com.cyber.speed.enums.SymbolEnum;
import com.cyber.speed.enums.SymbolTypeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class RewardResult {
   public double reward;
   public Map<String, List<String>> appliedCombinations;
   public List<String> appliedBonusSymbols;
   public List<String> findAppliedBonusSymbolsIfAny(Map<String, Integer> symbolFrequencies) {
      // Accumulate all BONUS symbols that exist in the provided frequencies map
      return symbolFrequencies.keySet().stream()
              .filter(symbol -> SymbolEnum.fromType(SymbolTypeEnum.BONUS.getType())
                      .stream()
                      .anyMatch(bonusSymbol -> bonusSymbol.equals(SymbolEnum.fromSymbol(symbol))))
              .toList();
   }


}
