package com.cyber.speed.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SymbolWrapper {
   @JsonProperty("standard_symbols")
   private List<StandardSymbol> standardSymbols;
   @JsonProperty("bonus_symbols")
   private BonusSymbol bonusSymbols;
}
