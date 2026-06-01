package com.github.vodobryshkin.cauchyproblem.domain.method;

import com.github.vodobryshkin.cauchyproblem.domain.FunctionOfTwoVariables;
import com.github.vodobryshkin.cauchyproblem.dto.Table;

import java.util.ArrayList;
import java.util.List;

public class RungeKuttaOfTheFourthOrder implements Method {
    private final FunctionOfTwoVariables f;

    public RungeKuttaOfTheFourthOrder(FunctionOfTwoVariables f) {
        this.f = f;
    }

    @Override
    public Table table(double y0, double x0, double xn, double h) {
        List<Double> xRow = new ArrayList<>();
        List<Double> yRow = new ArrayList<>();

        for (double x = x0; x <= xn; x += h) {
            xRow.add(x);
        }

        if (Math.abs(xn - xRow.getLast()) > 1e-9) {
            throw new IllegalArgumentException("Введённые границы интервала не соотносятся с введённым h.");
        }

        yRow.add(y0);

        for (int i = 0; i < xRow.size() - 1; i++) {
            double xi = xRow.get(i);
            double yi = yRow.get(i);

            double k1 = h * f.value(xi, yi);
            double k2 = h * f.value(xi + h/2, yi + k1/2);
            double k3 = h * f.value(xi + h/2, yi + k2/2);
            double k4 = h * f.value(xi + h, yi + k3);

            yRow.add(yi + 1/6 * (k1 + 2*k2 + 2*k3 + k4));
        }

        return new Table(xRow, yRow);
    }
}
