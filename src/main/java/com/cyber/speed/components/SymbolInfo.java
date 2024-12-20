package com.cyber.speed.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SymbolInfo {
   @JsonProperty("reward_multiplier")
   public double rewardMultiplier;
   private String type;
   private String impact; // Optional field
   private Integer extra; // Optional field
}
