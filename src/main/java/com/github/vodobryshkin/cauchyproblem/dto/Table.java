package com.github.vodobryshkin.cauchyproblem.dto;

import lombok.Value;

import java.util.List;

@Value
public class Table {
    List<Double> xRow;
    List<Double> yRow;
}
