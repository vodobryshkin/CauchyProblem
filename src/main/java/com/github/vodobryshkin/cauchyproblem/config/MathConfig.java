package com.github.vodobryshkin.cauchyproblem.config;

import com.github.vodobryshkin.cauchyproblem.domain.method.ExactSolutionFunction;
import com.github.vodobryshkin.cauchyproblem.domain.method.FunctionOfTwoVariables;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class MathConfig {
    private static final FunctionOfTwoVariables FIRST = new FunctionOfTwoVariables() {
        @Override
        public double value(double x, double y) {
            return y - x * x + 1;
        }

        @Override
        public String toString() {
            return "y' = y - x^2 + 1";
        }
    };

    private static final FunctionOfTwoVariables SECOND = new FunctionOfTwoVariables() {
        @Override
        public double value(double x, double y) {
            return 2 * x * (1 + y * y);
        }

        @Override
        public String toString() {
            return "y' = 2x(1 + y^2)";
        }
    };

    private static final FunctionOfTwoVariables THIRD = new FunctionOfTwoVariables() {
        @Override
        public double value(double x, double y) {
            return Math.sin(x) - y;
        }

        @Override
        public String toString() {
            return "y' = sin(x) - y";
        }
    };

    private static final FunctionOfTwoVariables FOURTH = new FunctionOfTwoVariables() {
        @Override
        public double value(double x, double y) {
            return y * Math.cos(x);
        }

        @Override
        public String toString() {
            return "y' = y * cos(x)";
        }
    };

    private static final ExactSolutionFunction FIRST_SOLUTION = (x, x0, y0) ->
            Math.pow(x + 1, 2) + (y0 - Math.pow(x0 + 1, 2)) * Math.exp(x - x0);

    private static final ExactSolutionFunction SECOND_SOLUTION = (x, x0, y0) ->
            Math.tan(x * x - x0 * x0 + Math.atan(y0));

    private static final ExactSolutionFunction THIRD_SOLUTION = (x, x0, y0) ->
            (Math.sin(x) - Math.cos(x)) / 2.0 + (y0 - (Math.sin(x0) - Math.cos(x0)) / 2.0) * Math.exp(-(x - x0));

    private static final ExactSolutionFunction FOURTH_SOLUTION = (x, x0, y0) ->
            y0 * Math.exp(Math.sin(x) - Math.sin(x0));

    @Bean
    public Map<Integer, FunctionOfTwoVariables> equations() {
        return Map.of(
                1, FIRST,
                2, SECOND,
                3, THIRD,
                4, FOURTH
        );
    }

    @Bean
    public Map<Integer, ExactSolutionFunction> solutions() {
        return Map.of(
                1, FIRST_SOLUTION,
                2, SECOND_SOLUTION,
                3, THIRD_SOLUTION,
                4, FOURTH_SOLUTION
        );
    }
}