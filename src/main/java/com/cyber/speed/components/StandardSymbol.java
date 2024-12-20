package com.cyber.speed.components;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StandardSymbol {
   private Integer column;
   private Integer row;
   public Map<String, Integer> symbols;
}
