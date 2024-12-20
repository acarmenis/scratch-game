package com.cyber.speed.actions;

public class MultiplyASymbol implements IBonusAction {

   @Override
   public Double apply(Double baseReward) {
      return baseReward * 5;
   }

}
