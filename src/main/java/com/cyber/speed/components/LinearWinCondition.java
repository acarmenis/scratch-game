package com.cyber.speed.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LinearWinCondition extends WinCondition {
   @JsonProperty("covered_areas")
   private List<List<String>> coveredAreas;
}
