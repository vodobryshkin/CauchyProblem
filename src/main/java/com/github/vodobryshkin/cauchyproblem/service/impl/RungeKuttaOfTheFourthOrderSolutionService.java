package com.github.vodobryshkin.cauchyproblem.service.impl;

import com.github.vodobryshkin.cauchyproblem.domain.FunctionOfTwoVariables;
import com.github.vodobryshkin.cauchyproblem.domain.accuracy.OrderOfAccuracy;
import com.github.vodobryshkin.cauchyproblem.domain.accuracy.RungeOrderOfAccuracy;
import com.github.vodobryshkin.cauchyproblem.domain.method.EulerMethod;
import com.github.vodobryshkin.cauchyproblem.domain.method.Method;
import com.github.vodobryshkin.cauchyproblem.domain.method.RungeKuttaOfTheFourthOrder;
import com.github.vodobryshkin.cauchyproblem.dto.Solution;
import com.github.vodobryshkin.cauchyproblem.dto.Table;
import com.github.vodobryshkin.cauchyproblem.service.spec.ODESolutionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RungeKuttaOfTheFourthOrderSolutionService implements ODESolutionService {
    private final OrderOfAccuracy orderOfAccuracy = new RungeOrderOfAccuracy(4);

    @Override
    public Solution solution(FunctionOfTwoVariables f, double y0, double x0, double xn, double h, double epsilon) {
        Method method = new RungeKuttaOfTheFourthOrder(f);

        Table solutionWithH = method.table(y0, x0, xn, h);
        Table solutionWithHalfH = method.table(y0, x0, xn, h / 2);

        List<Double> yHList = solutionWithH.getYRow();
        List<Double> yHalfHList = solutionWithHalfH.getYRow();

        double currentOrder = orderOfAccuracy.value(yHList, yHalfHList);

        return new Solution(solutionWithHalfH, currentOrder <= epsilon);
    }
}
