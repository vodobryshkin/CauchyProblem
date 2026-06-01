package com.github.vodobryshkin.cauchyproblem.domain.accuracy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ExactSolutionOrderOfAccuracy implements OrderOfAccuracy {
    @Override
    public double value(List<Double> yList, List<Double> exactYList) {
        if (yList.size() != exactYList.size()) {
            throw new IllegalArgumentException("Размеры списков значений y для полученного и точного решений должны быть одинаковы.");
        }

        List<Double> rList = new ArrayList<>();

        for (int i = 0; i < yList.size(); i++) {
            rList.add(Math.abs(exactYList.get(i) - yList.get(i)));
        }

        return rList.stream()
                .max(Comparator.naturalOrder())
                .orElseThrow(() -> new IllegalArgumentException("Список значений y для полученного и точного решений пуст."));
    }
}
