package com.cyber.speed.actions;

/**
 * Implement Concrete Action MultiplyBy10 Class
 */
public class MultiplyBy10 implements IBonusAction {
   @Override
   public Double apply(Double baseReward) {
      return baseReward * 10;
   }
}
