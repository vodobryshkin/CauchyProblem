package com.github.vodobryshkin.cauchyproblem.domain.solver;

import com.github.vodobryshkin.cauchyproblem.domain.method.ExactSolutionFunction;
import com.github.vodobryshkin.cauchyproblem.domain.method.FunctionOfTwoVariables;
import com.github.vodobryshkin.cauchyproblem.dto.Solution;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class MilnMethodSolutionSolverTest {
    private static final double DELTA = 1e-4;

    private static final FunctionOfTwoVariables FIRST = (x, y) -> y;
    private static final FunctionOfTwoVariables SECOND = Double::sum;
    private static final FunctionOfTwoVariables THIRD = (x, y) -> y - x * x + 1;

    private static final ExactSolutionFunction FIRST_EXACT = (x, x0, y0) -> y0 * Math.exp(x - x0);
    private static final ExactSolutionFunction SECOND_EXACT = (x, x0, y0) -> (y0 + x0 + 1) * Math.exp(x - x0) - x - 1;
    private static final ExactSolutionFunction THIRD_EXACT = (x, x0, y0) -> Math.pow(x + 1, 2) + (y0 - Math.pow(x0 + 1, 2)) * Math.exp(x - x0);

    private static final List<Double> FIRST_EXPECTED = List.of(1.0, 1.105171, 1.221403, 1.349858, 1.491824, 1.648721, 1.822119, 2.013752, 2.225541, 2.459603, 2.718282);
    private static final List<Double> SECOND_EXPECTED = List.of(1.0, 1.110342, 1.242805, 1.399717, 1.583649, 1.797442, 2.044237, 2.327505, 2.651081, 3.019205, 3.436563);
    private static final List<Double> THIRD_EXPECTED = List.of(0.5, 0.829293, 1.214076, 1.648922, 2.127213, 2.640836, 3.179919, 3.732370, 4.283453, 4.815138, 5.305431);

    static Stream<Arguments> correctArgumentsStream() {
        return Stream.of(
                Arguments.of(FIRST, FIRST_EXACT, 1.0, 0.0, 1.0, 0.1, 1.0, FIRST_EXPECTED),
                Arguments.of(SECOND, SECOND_EXACT, 1.0, 0.0, 1.0, 0.1, 1.0, SECOND_EXPECTED),
                Arguments.of(THIRD, THIRD_EXACT, 0.5, 0.0, 2.0, 0.2, 1.0, THIRD_EXPECTED)
        );
    }

    @Tag("unit")
    @ParameterizedTest
    @MethodSource("correctArgumentsStream")
    void miln_solver_correctly_calculates_correct_parameters(FunctionOfTwoVariables f, ExactSolutionFunction exactSolutionFunction, double y0, double x0, double xn, double h, double epsilon, List<Double> expected) {
        ODESolutionSolver sut = new MilnMethodSolutionSolver(exactSolutionFunction);

        Solution solution = sut.solution(f, y0, x0, xn, h, epsilon);
        List<Double> actual = solution.getTable().getYRow();

        assertThat(actual).hasSameSizeAs(expected);

        for (int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i)).as("y[%d]", i).isCloseTo(expected.get(i), within(DELTA));
        }
    }
}