package com.github.vodobryshkin.cauchyproblem.domain.solver;

import com.github.vodobryshkin.cauchyproblem.domain.method.FunctionOfTwoVariables;
import com.github.vodobryshkin.cauchyproblem.domain.accuracy.OrderOfAccuracy;
import com.github.vodobryshkin.cauchyproblem.domain.accuracy.RungeOrderOfAccuracy;
import com.github.vodobryshkin.cauchyproblem.domain.method.Method;
import com.github.vodobryshkin.cauchyproblem.domain.method.RungeKuttaOfTheFourthOrder;
import com.github.vodobryshkin.cauchyproblem.dto.Solution;
import com.github.vodobryshkin.cauchyproblem.dto.Table;

import java.util.List;

public class RungeKuttaOfTheFourthOrderSolutionSolver implements ODESolutionSolver {
    private final OrderOfAccuracy orderOfAccuracy = new RungeOrderOfAccuracy(4);

    @Override
    public Solution solution(FunctionOfTwoVariables f, double y0, double x0, double xn, double h, double epsilon) {
        Method method = new RungeKuttaOfTheFourthOrder(f);

        Table solutionWithH = method.table(y0, x0, xn, h);
        Table solutionWithHalfH = method.table(y0, x0, xn, h / 2);

        List<Double> yHList = solutionWithH.getYRow();
        List<Double> yHalfHList = solutionWithHalfH.getYRow();

        double currentOrder = orderOfAccuracy.value(yHList, yHalfHList);

        return new Solution(solutionWithH, currentOrder <= epsilon);
    }
}
