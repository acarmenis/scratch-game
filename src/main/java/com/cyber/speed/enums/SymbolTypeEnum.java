package com.cyber.speed.enums;

import com.cyber.speed.exceptions.ScratchGameException;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
public enum SymbolTypeEnum {

   STANDARD("standard"),
   BONUS("bonus");

   private final String type;

   SymbolTypeEnum(String type) {
      this.type = type;
   }

   public static SymbolTypeEnum fromType(String type) {
      return Arrays.stream(values())
              .filter(symbolType -> Objects.equals(symbolType.getType(), type))
              .findFirst()
              .orElseThrow(() -> new ScratchGameException("No SymbolType found for type: " + type));
   }
}
