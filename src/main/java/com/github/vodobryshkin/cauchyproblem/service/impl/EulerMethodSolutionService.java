package com.github.vodobryshkin.cauchyproblem.service.impl;

import com.github.vodobryshkin.cauchyproblem.domain.FunctionOfTwoVariables;
import com.github.vodobryshkin.cauchyproblem.domain.accuracy.OrderOfAccuracy;
import com.github.vodobryshkin.cauchyproblem.domain.accuracy.RungeOrderOfAccuracy;
import com.github.vodobryshkin.cauchyproblem.domain.method.EulerMethod;
import com.github.vodobryshkin.cauchyproblem.domain.method.Method;
import com.github.vodobryshkin.cauchyproblem.dto.Solution;
import com.github.vodobryshkin.cauchyproblem.dto.Table;
import com.github.vodobryshkin.cauchyproblem.service.spec.ODESolutionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EulerMethodSolutionService implements ODESolutionService {
    private final OrderOfAccuracy orderOfAccuracy = new RungeOrderOfAccuracy(1);

    @Override
    public Solution solution(FunctionOfTwoVariables f, double y0, double x0, double xn, double h, double epsilon) {
        Table solutionWithH;
        Table solutionWithHalfH;
        double currentOrder;

        double resultH;

        do {
            Method method = new EulerMethod(f);

            solutionWithH = method.table(y0, x0, xn, h);
            solutionWithHalfH = method.table(y0, x0, xn, h / 2);

            resultH = h / 2;

            List<Double> yHList = solutionWithH.getYRow();
            List<Double> yHalfHList = solutionWithHalfH.getYRow();

            currentOrder = orderOfAccuracy.value(yHList, yHalfHList);

        } while (currentOrder <= epsilon);


        return new Solution(solutionWithHalfH, resultH);
    }
}
