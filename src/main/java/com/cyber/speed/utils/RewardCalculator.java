package com.cyber.speed.utils;

import com.cyber.speed.actions.BonusActionFactory;
import com.cyber.speed.actions.IBonusAction;
import com.cyber.speed.components.LinearWinCondition;
import com.cyber.speed.components.ScratchGameConfig;
import com.cyber.speed.components.SymbolInfo;
import com.cyber.speed.components.WinCondition;
import com.cyber.speed.enums.ConditionEnum;
import com.cyber.speed.enums.ImpactEnum;
import com.cyber.speed.enums.SymbolTypeEnum;
import com.cyber.speed.evaulations.RewardResult;
import com.cyber.speed.exceptions.ScratchGameException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.logging.Logger;

import static com.cyber.speed.enums.ConditionEnum.SAME_SYMBOLS;
import static com.cyber.speed.enums.SymbolTypeEnum.BONUS;
import static com.cyber.speed.enums.SymbolTypeEnum.STANDARD;


@Getter
@RequiredArgsConstructor
public class RewardCalculator {

   static Logger logger = Logger.getLogger(RewardCalculator.class.getName());

   private static final String SYMBOL = "symbol_";
   private static final String BET_AMOUNT = "bet_amount x";
   private static final String APPLIED_BONUS_SYMBOL = "applied_bonus_symbol";
   private static final String REWARD_FOR_A_SPECIFIC_SYMBOL = "(reward for a specific symbol) x";
   private final ScratchGameConfig config;
   private final Map<String, Integer> symbolFrequencies;

   public RewardResult calculateRewards(String[][] matrix, int betAmount) {
      Map<String, List<String>> appliedCombinations = new HashMap<>();
      Double totalReward = 0.0;
      if(Objects.isNull(config)) throw new ScratchGameException("Config is null!");
      // The symbols with the winner frequencies - higher ones
      Map<String, Integer> symbolsWithWinnerFrequencies = getSymbolFrequenciesAboveOrEqualTo3();
      // Amount calculation without yet applied bonus
      totalReward = rewardBeforeAddingBonus(matrix, betAmount, appliedCombinations, symbolsWithWinnerFrequencies);
      // Applying the bonus on top of previous money
      Double totalRewardPlusBonus = applyBonusSymbols(totalReward, appliedCombinations, symbolsWithWinnerFrequencies);
      // add results to be shown
      RewardResult.RewardResultBuilder builder = RewardResult.builder();
      RewardResult result = builder.reward(Objects.nonNull(totalRewardPlusBonus)
                              ? totalRewardPlusBonus
                              : totalReward)
              .appliedCombinations(appliedCombinations)
              .build();
      result.setAppliedBonusSymbols(result.findAppliedBonusSymbolsIfAny(symbolsWithWinnerFrequencies));
      return result;
   }

   /**
    *
    * @param matrix the array with applied symbols
    * @param betAmount the bet amount
    * @param appliedCombinations map which keeps action information
    * @param symbolsWithWinnerFrequencies the symbols with highest frequencies - the winners and bonus ones
    * @return the amount before applying bonus
    */
   private Double rewardBeforeAddingBonus(String[][] matrix, int betAmount, Map<String, List<String>> appliedCombinations, Map<String, Integer> symbolsWithWinnerFrequencies){
      Double totalBeforeBonus = 0.0;
      // Iterate through each entry in the upWinningCombinations map
      for (Map.Entry<ConditionEnum, List<WinCondition>> entry : config.getUpWinningCombinations().entrySet()) {
         // Iterate through the list of WinCondition objects for the current key
         for (WinCondition winCondition : entry.getValue()) {
            // Evaluate the reward based on the condition type
            Double tempReward = Objects.equals(SAME_SYMBOLS, entry.getKey())
                    ? evaluateSameSymbol(matrix, winCondition, betAmount, appliedCombinations, symbolsWithWinnerFrequencies)
                    : evaluateLinearSymbol(matrix, (LinearWinCondition) winCondition, betAmount, appliedCombinations, symbolsWithWinnerFrequencies);
            // Accumulate the reward into the total reward
            totalBeforeBonus += tempReward;
         }
      }
      return totalBeforeBonus;
   }

   /**
    *
    * @param matrix
    * @param winCondition
    * @param betAmount
    * @param appliedCombinations
    * @param symbolsWithWinnerFrequencies
    * @return
    */
   public Double evaluateSameSymbol(String[][] matrix, WinCondition winCondition, double betAmount, Map<String, List<String>> appliedCombinations, Map<String, Integer> symbolsWithWinnerFrequencies) {
      Double tempReward = 0.0;
      for (Map.Entry<String, Integer> entry : symbolsWithWinnerFrequencies.entrySet()) {
         if (config.getSymbols().containsKey(entry.getKey())){
            SymbolInfo symbol = config.getSymbols().get(entry.getKey());
            SymbolTypeEnum symbolType = SymbolTypeEnum.fromType(symbol.getType());
            if (Objects.equals(STANDARD, symbolType)) {
               tempReward += handleStandardSymbol(entry, symbol, winCondition, betAmount, appliedCombinations);
            }
         }
      }
      return tempReward;
   }

   /**
    *
    * @param entry
    * @param symbol
    * @param winCondition
    * @param betAmount
    * @param appliedCombinations
    * @return
    */
   private Double handleStandardSymbol(Map.Entry<String, Integer> entry, SymbolInfo symbol, WinCondition winCondition, double betAmount, Map<String, List<String>> appliedCombinations) {
      Double tempReward = 0.0;
      // if frequency meets the condition of appearances
      if (entry.getValue() == winCondition.getCount()) {
         // 1. Add what is applied as info for later usage
         appliedCombinations.computeIfAbsent(SYMBOL.concat(entry.getKey()), k -> new ArrayList<>()).add(BET_AMOUNT.concat(String.valueOf(symbol.getRewardMultiplier())));
         appliedCombinations.computeIfAbsent(winCondition.getGroup(), k -> new ArrayList<>()).add(BET_AMOUNT.concat(REWARD_FOR_A_SPECIFIC_SYMBOL.concat(entry.getValue().toString())));
         // 2. Calculate local amount
         tempReward += calculateTempReward(entry.getKey(), betAmount) * entry.getValue();  //
      }
      return tempReward;
   }

   /**
    *
    * @param symbol
    * @param betAmount
    * @return
    */
   private Double calculateTempReward(String symbol, double betAmount) {
      IBonusAction action = BonusActionFactory.getAction(symbol);
      return action.apply(betAmount);
   }

   /**
    *
    * @param matrix
    * @param winCondition
    * @param betAmount
    * @param appliedCombinations
    * @param symbolsWithWinnerFrequencies
    * @return
    */
   public Double evaluateLinearSymbol(String[][] matrix, WinCondition winCondition, double betAmount, Map<String, List<String>> appliedCombinations, Map<String, Integer> symbolsWithWinnerFrequencies) {
      Double amount = 0.0;
      Map<Integer, List<Integer[]>> indexes = getCoveredAreaIndexes(winCondition);
      for (Map.Entry<String, Integer> entry : symbolsWithWinnerFrequencies.entrySet()) {
         if (config.getSymbols().containsKey(entry.getKey())) {
            SymbolInfo symbolInfo = config.getSymbols().get(entry.getKey());
            SymbolTypeEnum symbolType = SymbolTypeEnum.fromType(symbolInfo.getType());
            if (Objects.equals(STANDARD, symbolType)) {
               amount += doSymbolLinearMatching(matrix, indexes, entry.getKey(), appliedCombinations, winCondition, betAmount);
            }
         }
      }
      return amount;
   }

   /**
    * checks linear any condition mach
    * @param matrix
    * @param indexedCoordinates
    * @param symbol
    * @param appliedCombinations
    * @param winCondition
    * @param betAmount
    * @return
    */
   private Double doSymbolLinearMatching(String[][] matrix, Map<Integer, List<Integer[]>> indexedCoordinates, String symbol, Map<String, List<String>> appliedCombinations, WinCondition winCondition, double betAmount){
      Double amount = 0.0;
      for (Map.Entry<Integer, List<Integer[]>> row : indexedCoordinates.entrySet()) {
         boolean conditionMet = Boolean.TRUE;
         for (Integer[] index : row.getValue()) {
            if (!Objects.equals(symbol, matrix[index[0]][index[1]])) {
               conditionMet = Boolean.FALSE;
               break;
            }
         }
         if (conditionMet) {
            appliedCombinations.computeIfAbsent(winCondition.getGroup(), k -> new ArrayList<>()).add(symbol);
            amount += calculateTempReward(symbol, betAmount);
         }
      }
      return amount;
   }

   /**
    *
    * @param winCondition
    * @return
    */
   private Map<Integer, List<Integer[]>> getCoveredAreaIndexes(WinCondition winCondition){
     LinearWinCondition condition = ((LinearWinCondition) winCondition);
     if (CollectionUtils.isEmpty(condition.getCoveredAreas())) return Collections.emptyMap();
     Map<Integer, List<Integer[]>> mapOfIndexes = new HashMap<>();
     for(int i = 0 ; i < condition.getCoveredAreas().size(); i++){
        List<Integer[]> indexes = new ArrayList<>();
        condition.getCoveredAreas().get(i).forEach(coordinate -> {
           String [] currentStringIndexes = coordinate.trim().split(":");
           indexes.add(new Integer[]{Integer.parseInt(currentStringIndexes[0]), Integer.parseInt(currentStringIndexes[1])});
        });
        mapOfIndexes.put(i, indexes);
     }
     return mapOfIndexes;
   }

   /**
    *
    * @param tempSumAmount
    * @param appliedCombinations
    * @param symbolsWithWinnerFrequencies
    * @return
    */
   private Double applyBonusSymbols(double tempSumAmount, Map<String, List<String>> appliedCombinations, Map<String, Integer> symbolsWithWinnerFrequencies) {
      Double localBonus = 0.0;
      for (Map.Entry<String, Integer> entry : symbolsWithWinnerFrequencies.entrySet()) {
         if (config.getSymbols().containsKey(entry.getKey())) {
            SymbolInfo symbolInfo = config.getSymbols().get(entry.getKey());
            SymbolTypeEnum symbolType = SymbolTypeEnum.fromType(symbolInfo.getType());
            if (Objects.equals(BONUS, symbolType)) {
               ImpactEnum impact = ImpactEnum.fromImpact(symbolInfo.getImpact());
               if (Objects.equals(ImpactEnum.MISS, impact)){
                  appliedCombinations.computeIfAbsent(entry.getKey(), k -> new ArrayList<>()).add(ImpactEnum.MISS.getImpact());
                  return null;
               }
               appliedCombinations.computeIfAbsent(APPLIED_BONUS_SYMBOL, k -> new ArrayList<>()).add(entry.getKey());
               localBonus += calculateTempReward(entry.getKey(), tempSumAmount);
            }
         }
      }
     return localBonus;
   }

   /**
    * Symbol Frequencies Above Or Equal To 3 and bonus ones
    * @return
    */
   private Map<String, Integer> getSymbolFrequenciesAboveOrEqualTo3() {
      Map<String, Integer> map = new HashMap<>();
      symbolFrequencies.forEach((k,v) -> {
         if (v >= config.getWinCombinations().getSameSymbol3Times().getCount() || config.getProbabilities().getBonusSymbols().getSymbols().containsKey(k)) {
            map.put(k, v);
         }
      });
      return map;
   }

}
