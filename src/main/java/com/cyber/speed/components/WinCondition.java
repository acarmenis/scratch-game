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
public class WinCondition {
   @JsonProperty("reward_multiplier")
   private double rewardMultiplier;
   private String when;
   private int count;
   private String group;
}
