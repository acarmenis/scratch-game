package com.cyber.speed.actions;

public class MultiplyDSymbol implements IBonusAction {

   @Override
   public Double apply(Double baseReward) {
      return baseReward * 2;
   }
}
