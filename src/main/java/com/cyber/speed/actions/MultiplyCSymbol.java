package com.cyber.speed.actions;

public class MultiplyCSymbol implements IBonusAction {

   @Override
   public Double apply(Double baseReward) {
      return baseReward * 2.5;
   }
}

