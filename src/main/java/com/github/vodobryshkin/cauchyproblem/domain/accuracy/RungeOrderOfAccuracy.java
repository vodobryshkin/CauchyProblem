package com.github.vodobryshkin.cauchyproblem.domain.accuracy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RungeOrderOfAccuracy implements OrderOfAccuracy {
    private final int p;

    public RungeOrderOfAccuracy(int p) {
        this.p = p;
    }

    @Override
    public double value(List<Double> yHList, List<Double> yHalfHList) {
        List<Double> rList = new ArrayList<>();

        if (yHList.size() != yHalfHList.size()) {
            throw new IllegalArgumentException("Размеры списков значений y для h и h/2 должны быть одинаковы.");
        }

        if (yHList.size() < 2) {
            throw new IllegalArgumentException("Списки y для h и h/2 должны состоять как минимум из двух значений.");
        }

        double h = yHList.getFirst() - yHList.get(1);

        for (int i = 1; i < yHList.size() - 1; i++) {
            if (yHList.get(i + 1) - yHList.get(i) != h) {
                throw new IllegalArgumentException("Разница между элементами списков значений y для h должна быть постоянна.");
            }
        }

        double halfH = yHalfHList.getFirst() - yHalfHList.get(1);

        if (Math.abs(h/2 - halfH) > 1e-9) {
            throw new IllegalArgumentException("Разница между элементами списков значений во втором списке должна равняться h/2.");
        }

        for (int i = 1; i < yHList.size() - 1; i++) {
            if (yHalfHList.get(i + 1) - yHalfHList.get(i) != halfH) {
                throw new IllegalArgumentException("Разница между элементами списков значений y для h/2 должна быть постоянна.");
            }
        }

        for (int i = 0; i < yHList.size(); i++) {
            rList.add(rungeRule(yHList.get(i), yHalfHList.get(i), h, halfH));
        }

        return rList.stream()
                .max(Comparator.naturalOrder())
                .orElseThrow(() -> new IllegalArgumentException("Список значений y для h и h/2 пуст."));
    }

    private double rungeRule(double yH, double yHalfH, double h, double halfH) {
        return Math.abs(Math.pow(yH, h) - Math.pow(yHalfH, halfH)) / (Math.pow(2, p) - 1);
    }
}
