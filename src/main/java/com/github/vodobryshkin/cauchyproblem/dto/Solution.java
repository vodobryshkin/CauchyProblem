package com.github.vodobryshkin.cauchyproblem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class Solution {
    String method;
    String f;
    double y0;
    double x0;
    double xn;
    double h;
    double epsilon;

    @JsonProperty("exact_solution")
    List<Double> exactSolution;
    Table table;
    @JsonProperty("error_estimate")
    double errorEstimate;

    @JsonProperty("accuracy_reached")
    boolean accuracyReached;
}
