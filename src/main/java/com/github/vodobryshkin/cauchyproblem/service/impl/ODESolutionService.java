package com.github.vodobryshkin.cauchyproblem.service.impl;

import com.github.vodobryshkin.cauchyproblem.domain.method.ExactSolutionFunction;
import com.github.vodobryshkin.cauchyproblem.domain.method.FunctionOfTwoVariables;
import com.github.vodobryshkin.cauchyproblem.domain.solver.EulerMethodSolutionSolver;
import com.github.vodobryshkin.cauchyproblem.domain.solver.MilnMethodSolutionSolver;
import com.github.vodobryshkin.cauchyproblem.domain.solver.ODESolutionSolver;
import com.github.vodobryshkin.cauchyproblem.domain.solver.RungeKuttaOfTheFourthOrderSolutionSolver;
import com.github.vodobryshkin.cauchyproblem.dto.ODERequest;
import com.github.vodobryshkin.cauchyproblem.dto.Solution;
import com.github.vodobryshkin.cauchyproblem.service.spec.IODESolutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ODESolutionService implements IODESolutionService {
    private final Map<Integer, FunctionOfTwoVariables> equations;
    private final Map<Integer, ExactSolutionFunction> exactSolutions;

    @Override
    public Solution result(ODERequest request) {
        int number = request.getNumber();
        String methodName = request.getMethod();
        double y0 = request.getY0();
        double x0 = request.getX0();
        double xn = request.getXn();
        double h = request.getH();
        double epsilon = request.getEpsilon();

        ODESolutionSolver solver = switch (methodName) {
            case "euler" -> new EulerMethodSolutionSolver();
            case "runge" -> new RungeKuttaOfTheFourthOrderSolutionSolver();
            case "miln" -> new MilnMethodSolutionSolver(exactSolutions.get(number));
            case null, default -> throw new IllegalArgumentException("Решение данным методом еще не реализовано.");
        };

        return solver.solution(equations.get(number), y0, x0, xn, h, epsilon);
    }
}
