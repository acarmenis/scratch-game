package com.cyber.speed.actions;

import java.util.HashMap;
import java.util.Map;

public class BonusActionFactory {

   private static final Map<String, IBonusAction> actions = new HashMap<>();

   static {
      actions.put("A", new MultiplyASymbol());
      actions.put("B", new MultiplyBSymbol());
      actions.put("C", new MultiplyCSymbol());
      actions.put("D", new MultiplyDSymbol());
      actions.put("E", new MultiplyESymbol());
      actions.put("F", new MultiplyFSymbol());
      actions.put("10x", new MultiplyBy10());
      actions.put("5x", new MultiplyBy5());
      actions.put("+1000", new Add1000());
      actions.put("+500", new Add500());
      actions.put("MISS", new MissAction());
   }

   public static IBonusAction getAction(String key) {
      return actions.getOrDefault(key, new MissAction()); // Default to MISS
   }

}
