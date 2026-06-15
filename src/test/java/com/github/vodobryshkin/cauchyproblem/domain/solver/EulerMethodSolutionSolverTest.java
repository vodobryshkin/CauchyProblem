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

public class EulerMethodSolutionSolverTest {
    private static final double DELTA = 1e-6;

    private static final FunctionOfTwoVariables FIRST = (x, y) -> y;
    private static final FunctionOfTwoVariables SECOND = Double::sum;
    private static final FunctionOfTwoVariables THIRD = (x, y) -> y - x * x + 1;

    private static final ExactSolutionFunction FIRST_EXACT = (x, x0, y0) -> y0 * Math.exp(x - x0);
    private static final ExactSolutionFunction SECOND_EXACT = (x, x0, y0) -> (y0 + x0 + 1) * Math.exp(x - x0) - x - 1;
    private static final ExactSolutionFunction THIRD_EXACT = (x, x0, y0) -> Math.pow(x + 1, 2) + (y0 - Math.pow(x0 + 1, 2)) * Math.exp(x - x0);

    private static final List<Double> FIRST_EXPECTED = List.of(1.0, 1.1, 1.21, 1.331, 1.4641, 1.61051, 1.771561, 1.948717, 2.143589, 2.357948, 2.593742);
    private static final List<Double> SECOND_EXPECTED = List.of(1.0, 1.1, 1.22, 1.362, 1.5282, 1.72102, 1.943122, 2.197434, 2.487178, 2.815895, 3.187485);
    private static final List<Double> THIRD_EXPECTED = List.of(0.5, 0.8, 1.152, 1.5504, 1.98848, 2.458176, 2.949811, 3.451773, 3.950128, 4.428154, 4.865785);

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
    void euler_solver_correctly_calculates_correct_parameters(FunctionOfTwoVariables f, ExactSolutionFunction exactSolutionFunction, double y0, double x0, double xn, double h, double epsilon, List<Double> expected) {
        ODESolutionSolver sut = new EulerMethodSolutionSolver(exactSolutionFunction);

        Solution solution = sut.solution(f, y0, x0, xn, h, epsilon);
        List<Double> actual = solution.getTable().getYRow();

        assertThat(actual).hasSameSizeAs(expected);

        for (int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i)).as("y[%d]", i).isCloseTo(expected.get(i), within(DELTA));
        }
    }
}