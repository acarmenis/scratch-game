package com.cyber.speed.actions;

public class MultiplyBSymbol implements IBonusAction {

   @Override
   public Double apply(Double baseReward) {
      return baseReward * 3;
   }
}

