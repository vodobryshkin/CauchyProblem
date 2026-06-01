package com.github.vodobryshkin.cauchyproblem.service.spec;

import com.github.vodobryshkin.cauchyproblem.domain.FunctionOfTwoVariables;
import com.github.vodobryshkin.cauchyproblem.dto.Solution;

public interface ODESolutionService {
    Solution solution(FunctionOfTwoVariables f, double y0, double x0, double xn, double h, double epsilon);
}
