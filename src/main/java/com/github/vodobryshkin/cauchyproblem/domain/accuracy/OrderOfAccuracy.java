package com.github.vodobryshkin.cauchyproblem.domain.accuracy;

import java.util.List;

public interface OrderOfAccuracy {
    double value(List<Double> yHList, List<Double> yHalfHList);
}
