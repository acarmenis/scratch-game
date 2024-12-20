package com.cyber.speed.actions;


/**
 * Implement Concrete Action Add1000 Class
 */
public class Add1000 implements IBonusAction{
   @Override
   public Double apply(Double baseReward) {
      return baseReward + 1000;
   }
}
