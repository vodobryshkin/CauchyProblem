package com.github.vodobryshkin.cauchyproblem.domain.solver;

import com.github.vodobryshkin.cauchyproblem.domain.method.FunctionOfTwoVariables;
import com.github.vodobryshkin.cauchyproblem.dto.Solution;

public interface ODESolutionSolver {
    Solution solution(FunctionOfTwoVariables f, double y0, double x0, double xn, double h, double epsilon);
}
