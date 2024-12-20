package com.cyber.speed.enums;

import com.cyber.speed.exceptions.ScratchGameException;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
public enum ImpactEnum {

   MULTIPLY_REWARD("multiply_reward"),
   EXTRA_BONUS("extra_bonus"),
   MISS("miss");

   private final String impact;

   ImpactEnum(String impact) {
      this.impact = impact;
   }

   public static ImpactEnum fromImpact(String impact) {
      return Arrays.stream(values())
              .filter(value -> Objects.equals(value.getImpact(), impact))
              .findFirst()
              .orElseThrow(() -> new ScratchGameException("No symbol found for impact: " + impact));
   }

}
