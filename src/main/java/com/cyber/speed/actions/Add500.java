package com.cyber.speed.actions;

/**
 * Implement Concrete Action Add500 Class
 */
public class Add500 implements IBonusAction {
   @Override
   public Double apply(Double baseReward) {
      return baseReward + 500;
   }
}
