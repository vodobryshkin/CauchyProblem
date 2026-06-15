package com.github.vodobryshkin.cauchyproblem.domain.solver;

import com.github.vodobryshkin.cauchyproblem.domain.accuracy.ExactSolutionOrderOfAccuracy;
import com.github.vodobryshkin.cauchyproblem.domain.accuracy.OrderOfAccuracy;
import com.github.vodobryshkin.cauchyproblem.domain.method.ExactSolutionFunction;
import com.github.vodobryshkin.cauchyproblem.domain.method.FunctionOfTwoVariables;
import com.github.vodobryshkin.cauchyproblem.domain.method.Method;
import com.github.vodobryshkin.cauchyproblem.domain.method.MilnMethod;
import com.github.vodobryshkin.cauchyproblem.dto.Solution;
import com.github.vodobryshkin.cauchyproblem.dto.Table;

import java.util.ArrayList;
import java.util.List;

public class MilnMethodSolutionSolver implements ODESolutionSolver {
    private static final int MAX_ITER = 30;

    private final OrderOfAccuracy orderOfAccuracy = new ExactSolutionOrderOfAccuracy();
    private final ExactSolutionFunction exactSolutionFunction;

    public MilnMethodSolutionSolver(ExactSolutionFunction exactSolutionFunction) {
        this.exactSolutionFunction = exactSolutionFunction;
    }

    @Override
    public Solution solution(FunctionOfTwoVariables f, double y0, double x0, double xn, double h, double epsilon) {
        double currentH = h;

        for (int i = 0; i < MAX_ITER; i++) {
            Method method = new MilnMethod(f, epsilon);

            Table solution = method.table(y0, x0, xn, currentH);

            List<Double> exactSolution = new ArrayList<>();

            for (int j = 0; j < solution.getXRow().size(); j++) {
                double x = solution.getXRow().get(j);

                exactSolution.add(exactSolutionFunction.value(x, x0, y0));
            }

            double currentError = orderOfAccuracy.value(solution.getYRow(), exactSolution);

            if (currentError <= epsilon) {
                return new Solution("miln", f.toString(), y0, x0, xn, currentH, epsilon, exactSolution, solution, currentError, true);
            }

            currentH /= 2;
        }

        throw new IllegalArgumentException("Не удалось достичь заданной точности методом Милна.");
    }
}