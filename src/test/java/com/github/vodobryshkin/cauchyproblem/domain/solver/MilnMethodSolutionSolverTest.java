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

public class MilnMethodSolutionSolverTest {
    private static final double DELTA = 1e-4;

    private static final FunctionOfTwoVariables FIRST = (x, y) -> y;
    private static final FunctionOfTwoVariables SECOND = Double::sum;
    private static final FunctionOfTwoVariables THIRD = (x, y) -> y - x * x + 1;

    private static final List<Double> FIRST_EXACT = List.of(1.0, 1.105171, 1.221403, 1.349859, 1.491825, 1.648721, 1.822119, 2.013753, 2.225541, 2.459603, 2.718282);
    private static final List<Double> SECOND_EXACT = List.of(1.0, 1.110342, 1.242806, 1.399718, 1.583649, 1.797443, 2.044238, 2.327505, 2.651082, 3.019206, 3.436564);
    private static final List<Double> THIRD_EXACT = List.of(0.5, 0.829299, 1.214088, 1.648941, 2.127230, 2.640859, 3.179942, 3.732400, 4.283484, 4.815176, 5.305472);

    private static final List<Double> FIRST_EXPECTED = List.of(1.0, 1.105171, 1.221403, 1.349858, 1.491824, 1.648721, 1.822119, 2.013752, 2.225541, 2.459603, 2.718282);
    private static final List<Double> SECOND_EXPECTED = List.of(1.0, 1.110342, 1.242805, 1.399717, 1.583649, 1.797442, 2.044237, 2.327505, 2.651081, 3.019205, 3.436563);
    private static final List<Double> THIRD_EXPECTED = List.of(0.5, 0.829293, 1.214076, 1.648922, 2.127213, 2.640836, 3.179919, 3.732370, 4.283453, 4.815138, 5.305431);

    static Stream<Arguments> correctArgumentsStream() {
        return Stream.of(
                Arguments.of(FIRST, 1.0, 0.0, 1.0, 0.1, 1e-4, FIRST_EXACT, FIRST_EXPECTED),
                Arguments.of(SECOND, 1.0, 0.0, 1.0, 0.1, 1e-4, SECOND_EXACT, SECOND_EXPECTED),
                Arguments.of(THIRD, 0.5, 0.0, 2.0, 0.2, 1e-4, THIRD_EXACT, THIRD_EXPECTED)
        );
    }

    @Tag("unit")
    @ParameterizedTest
    @MethodSource("correctArgumentsStream")
    void miln_solver_correctly_calculates_correct_parameters(FunctionOfTwoVariables f, double y0, double x0, double xn, double h, double epsilon, List<Double> exactSolution, List<Double> expected) {
        ODESolutionSolver sut = new MilnMethodSolutionSolver(exactSolution);

        Solution solution = sut.solution(f, y0, x0, xn, h, epsilon);
        List<Double> actual = solution.getTable().getYRow();

        assertThat(actual).hasSameSizeAs(expected);

        for (int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i)).as("y[%d]", i).isCloseTo(expected.get(i), within(DELTA));
        }
    }
}