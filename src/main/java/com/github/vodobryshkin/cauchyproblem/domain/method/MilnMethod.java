package com.github.vodobryshkin.cauchyproblem.domain.method;

import com.github.vodobryshkin.cauchyproblem.domain.FunctionOfTwoVariables;
import com.github.vodobryshkin.cauchyproblem.dto.Table;

import java.util.ArrayList;
import java.util.List;

public class MilnMethod implements Method {
    private final FunctionOfTwoVariables f;
    private final Method prePredictMethod;

    public MilnMethod(FunctionOfTwoVariables f) {
        this.f = f;
        prePredictMethod = new RungeKuttaOfTheFourthOrder(f);
    }

    @Override
    public Table table(double y0, double x0, double xn, double h) {
        List<Double> xRow = new ArrayList<>();

        for (double x = x0; x <= xn; x += h) {
            xRow.add(x);
        }

        if (Math.abs(xn - xRow.getLast()) > 1e-9) {
            throw new IllegalArgumentException("Введённые границы интервала не соотносятся с введённым h.");
        }

        if (xRow.size() < 4)  {
            throw new IllegalArgumentException("Метод Милна можно использовать минимум для трёх узлов.");
        }

        List<Double> yRow = prePredictMethod.table(y0, x0, xRow.get(2), h).getYRow();

        for (int i = 3; i < xRow.size() - 1; i++) {
            double xi = xRow.get(i);

            double yPrev3 = yRow.get(i - 3);
            double fPrev2 = f.value(xRow.get(i - 2), yRow.get(i - 2));
            double fPrev1 = f.value(xRow.get(i - 1), yRow.get(i - 1));
            double fIn = f.value(xRow.get(i), yRow.get(i));

            double predictY = yPrev3 + 4*h/3 * (2*fPrev2 - fPrev1 + 2*fIn);
            double predictDerivative = f.value(xi, predictY);

            double yPrev1 = yRow.get(i - 1);

            yRow.add(yPrev1 + h/3 * (fPrev1 + 4*fIn + predictDerivative));
        }

        return new Table(xRow, yRow);
    }
}
