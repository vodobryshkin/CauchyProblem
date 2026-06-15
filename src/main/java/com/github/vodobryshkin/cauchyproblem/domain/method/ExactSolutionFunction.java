package com.github.vodobryshkin.cauchyproblem.domain.method;

public interface ExactSolutionFunction {
    double value(double x, double x0, double y0);
}
