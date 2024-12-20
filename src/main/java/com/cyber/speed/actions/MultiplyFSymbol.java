package com.cyber.speed.actions;

public class MultiplyFSymbol implements IBonusAction {

   @Override
   public Double apply(Double baseReward) {
      return baseReward * 1;
   }
}