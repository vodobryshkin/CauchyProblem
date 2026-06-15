package com.github.vodobryshkin.cauchyproblem.domain.method;

import com.github.vodobryshkin.cauchyproblem.dto.Table;

import java.util.ArrayList;
import java.util.List;

public class EulerMethod implements Method {
    private final FunctionOfTwoVariables f;

    public EulerMethod(FunctionOfTwoVariables f) {
        this.f = f;
    }

    @Override
    public Table table(double y0, double x0, double xn, double h) {
        List<Double> xRow = new ArrayList<>();
        List<Double> yRow = new ArrayList<>();

        for (double x = x0; x <= xn + h / 2; x += h) {
            xRow.add(x);
        }

        if (Math.abs(xn - xRow.getLast()) > 1e-9) {
            System.out.printf("%f %f\n", xn, xRow.getLast());
            throw new IllegalArgumentException("Введённые границы интервала не соотносятся с введённым h.");
        }

        yRow.add(y0);

        for (int i = 0; i < xRow.size() - 1; i++) {
            double xi = xRow.get(i);
            double yi = yRow.get(i);

            yRow.add(yi + h * f.value(xi, yi));
        }

        return new Table(xRow, yRow);
    }
}
