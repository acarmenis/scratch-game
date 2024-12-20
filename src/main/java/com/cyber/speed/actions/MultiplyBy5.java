package com.cyber.speed.actions;

/**
 * Implement Concrete Action MultiplyBy5 Class
 */
public class MultiplyBy5 implements IBonusAction {

   @Override
   public Double apply(Double baseReward) {
      return baseReward * 5;
   }

}
