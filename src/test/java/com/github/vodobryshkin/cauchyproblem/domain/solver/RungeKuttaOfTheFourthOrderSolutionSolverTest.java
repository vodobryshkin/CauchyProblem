package com.github.vodobryshkin.cauchyproblem.domain.solver;

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

public class RungeKuttaOfTheFourthOrderSolutionSolverTest {
    private static final double DELTA = 1e-6;

    private static final FunctionOfTwoVariables FIRST = (x, y) -> y;
    private static final FunctionOfTwoVariables SECOND = Double::sum;
    private static final FunctionOfTwoVariables THIRD = (x, y) -> y - x * x + 1;

    private static final List<Double> FIRST_EXPECTED = List.of(1.0, 1.105171, 1.221403, 1.349858, 1.491824, 1.648721, 1.822118, 2.013752, 2.225540, 2.459601, 2.718280);
    private static final List<Double> SECOND_EXPECTED = List.of(1.0, 1.110342, 1.242805, 1.399717, 1.583648, 1.797441, 2.044236, 2.327503, 2.651079, 3.019203, 3.436559);
    private static final List<Double> THIRD_EXPECTED = List.of(0.5, 0.829293, 1.214076, 1.648922, 2.127203, 2.640823, 3.179894, 3.732340, 4.283409, 4.815086, 5.305363);

    static Stream<Arguments> correctArgumentsStream() {
        return Stream.of(
                Arguments.of(FIRST, 1.0, 0.0, 1.0, 0.1, 1e-4, FIRST_EXPECTED),
                Arguments.of(SECOND, 1.0, 0.0, 1.0, 0.1, 1e-4, SECOND_EXPECTED),
                Arguments.of(THIRD, 0.5, 0.0, 2.0, 0.2, 1e-4, THIRD_EXPECTED)
        );
    }

    @Tag("unit")
    @ParameterizedTest
    @MethodSource("correctArgumentsStream")
    void runge_kutta_of_the_fourth_order_solver_correctly_calculates_correct_parameters(FunctionOfTwoVariables f, double y0, double x0, double xn, double h, double epsilon, List<Double> expected) {
        ODESolutionSolver sut = new RungeKuttaOfTheFourthOrderSolutionSolver();

        Solution solution = sut.solution(f, y0, x0, xn, h, epsilon);
        List<Double> actual = solution.getTable().getYRow();

        assertThat(actual).hasSameSizeAs(expected);

        for (int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i)).as("y[%d]", i).isCloseTo(expected.get(i), within(DELTA));
        }
    }
}