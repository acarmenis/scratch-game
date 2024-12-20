package com.cyber.speed.enums;

import com.cyber.speed.exceptions.ScratchGameException;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
public enum ConditionEnum {
   SAME_SYMBOLS("same_symbols"),
   LINEAR_SYMBOLS("linear_symbols");

   private final String condition;

   ConditionEnum(String condition) {
      this.condition = condition;
   }

   public static ConditionEnum fromCondition(String condition) {
      return Arrays.stream(values())
              .filter(value -> Objects.equals(value.getCondition(), condition))
              .findFirst()
              .orElseThrow(() -> new ScratchGameException("No Condition Enum found for condition: " + condition));
   }

}
