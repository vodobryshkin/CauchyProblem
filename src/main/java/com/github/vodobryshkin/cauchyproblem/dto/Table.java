package com.github.vodobryshkin.cauchyproblem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class Table {
    @JsonProperty("x_row")
    List<Double> xRow;

    @JsonProperty("y_row")
    List<Double> yRow;
}
