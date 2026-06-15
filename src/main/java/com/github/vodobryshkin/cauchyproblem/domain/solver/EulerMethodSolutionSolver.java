package com.github.vodobryshkin.cauchyproblem.domain.solver;

import com.github.vodobryshkin.cauchyproblem.domain.accuracy.OrderOfAccuracy;
import com.github.vodobryshkin.cauchyproblem.domain.accuracy.RungeOrderOfAccuracy;
import com.github.vodobryshkin.cauchyproblem.domain.method.EulerMethod;
import com.github.vodobryshkin.cauchyproblem.domain.method.ExactSolutionFunction;
import com.github.vodobryshkin.cauchyproblem.domain.method.FunctionOfTwoVariables;
import com.github.vodobryshkin.cauchyproblem.domain.method.Method;
import com.github.vodobryshkin.cauchyproblem.dto.Solution;
import com.github.vodobryshkin.cauchyproblem.dto.Table;

import java.util.ArrayList;
import java.util.List;

public class EulerMethodSolutionSolver implements ODESolutionSolver {
    private final OrderOfAccuracy orderOfAccuracy = new RungeOrderOfAccuracy(1);
    private final ExactSolutionFunction exactSolutionFunction;

    public EulerMethodSolutionSolver(ExactSolutionFunction exactSolutionFunction) {
        this.exactSolutionFunction = exactSolutionFunction;
    }

    @Override
    public Solution solution(FunctionOfTwoVariables f, double y0, double x0, double xn, double h, double epsilon) {
        Method method = new EulerMethod(f);

        Table solutionWithH = method.table(y0, x0, xn, h);
        Table solutionWithHalfH = method.table(y0, x0, xn, h / 2);

        List<Double> yHList = solutionWithH.getYRow();
        List<Double> yHalfHList = solutionWithHalfH.getYRow();

        double currentError = orderOfAccuracy.value(yHList, yHalfHList);

        List<Double> exactSolution = new ArrayList<>();

        for (int i = 0; i < solutionWithH.getXRow().size(); i++) {
            double x = solutionWithH.getXRow().get(i);

            exactSolution.add(exactSolutionFunction.value(x, x0, y0));
        }

        return new Solution("euler", f.toString(), y0, x0, xn, h, epsilon, exactSolution, solutionWithH, currentError, currentError <= epsilon);
    }
}