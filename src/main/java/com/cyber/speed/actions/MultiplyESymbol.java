package com.cyber.speed.actions;

public class MultiplyESymbol implements IBonusAction {

   @Override
   public Double apply(Double baseReward) {
      return baseReward * 1.2;
   }
}