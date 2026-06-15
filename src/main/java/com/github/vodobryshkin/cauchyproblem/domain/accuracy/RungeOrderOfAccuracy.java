package com.github.vodobryshkin.cauchyproblem.domain.accuracy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RungeOrderOfAccuracy implements OrderOfAccuracy {
    private final int p;

    public RungeOrderOfAccuracy(int p) {
        if (p <= 0) {
            throw new IllegalArgumentException("Порядок точности метода должен быть положительным.");
        }

        this.p = p;
    }

    @Override
    public double value(List<Double> yHList, List<Double> yHalfHList) {
        List<Double> rList = new ArrayList<>();

        if (yHList == null || yHalfHList == null) {
            throw new IllegalArgumentException("Списки значений y для h и h/2 не должны быть null.");
        }

        if (yHList.size() < 2) {
            throw new IllegalArgumentException("Список значений y для h должен состоять как минимум из двух значений.");
        }

        if (yHalfHList.size() < 3) {
            throw new IllegalArgumentException("Список значений y для h/2 должен состоять как минимум из трех значений.");
        }

        int expectedHalfHSize = 2 * yHList.size() - 1;

        if (yHalfHList.size() != expectedHalfHSize) {
            throw new IllegalArgumentException(
                    "Размер списка значений y для h/2 должен быть равен 2 * size(yHList) - 1."
            );
        }

        for (int i = 0; i < yHList.size(); i++) {
            double yH = yHList.get(i);
            double yHalfH = yHalfHList.get(2 * i);

            double r = rungeRule(yH, yHalfH);

            if (!Double.isFinite(r)) {
                throw new IllegalArgumentException("Получено некорректное значение погрешности по правилу Рунге.");
            }

            rList.add(r);
        }

        return rList.stream()
                .max(Comparator.naturalOrder())
                .orElseThrow(() -> new IllegalArgumentException("Список значений погрешностей пуст."));
    }

    private double rungeRule(double yH, double yHalfH) {
        return Math.abs(yH - yHalfH) / (Math.pow(2.0, p) - 1.0);
    }
}