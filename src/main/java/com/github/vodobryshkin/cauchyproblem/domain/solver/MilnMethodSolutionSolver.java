package com.github.vodobryshkin.cauchyproblem.domain.solver;

import com.github.vodobryshkin.cauchyproblem.domain.method.FunctionOfTwoVariables;
import com.github.vodobryshkin.cauchyproblem.domain.accuracy.ExactSolutionOrderOfAccuracy;
import com.github.vodobryshkin.cauchyproblem.domain.accuracy.OrderOfAccuracy;
import com.github.vodobryshkin.cauchyproblem.domain.method.Method;
import com.github.vodobryshkin.cauchyproblem.domain.method.MilnMethod;
import com.github.vodobryshkin.cauchyproblem.dto.Solution;
import com.github.vodobryshkin.cauchyproblem.dto.Table;

import java.util.List;

public class MilnMethodSolutionSolver implements ODESolutionSolver {
    private final OrderOfAccuracy orderOfAccuracy = new ExactSolutionOrderOfAccuracy();
    private final List<Double> exactSolution;

    public MilnMethodSolutionSolver(List<Double> exactSolution) {
        this.exactSolution = exactSolution;
    }

    @Override
    public Solution solution(FunctionOfTwoVariables f, double y0, double x0, double xn, double h, double epsilon) {
        Method method = new MilnMethod(f);

        Table solution = method.table(y0, x0, xn, h);

        double currentOrder = orderOfAccuracy.value(solution.getYRow(), exactSolution);

        return new Solution(solution, currentOrder <= epsilon);
    }
}
