package com.github.vodobryshkin.cauchyproblem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class Solution {
    String method;
    String f;
    double y0;
    double x0;
    double xn;
    double h;
    double epsilon;

    Table table;
    @JsonProperty("order_of_accuracy")
    double orderOfAccuracy;

    @JsonProperty("accuracy_reached")
    boolean accuracyReached;
}
