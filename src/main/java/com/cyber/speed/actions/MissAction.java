package com.cyber.speed.actions;

/**
 * Implement Concrete Action MissAction Class
 */
public class MissAction implements IBonusAction {

   @Override
   public Double apply(Double baseReward) {
      return null; // MISS results in null
   }

}
