package com.github.vodobryshkin.cauchyproblem.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ODERequest {
    private static final double TAN_ASYMPTOTE = 1e-6;

    @Min(value = 1, message = "Номер уравнения должен быть не меньше 1.")
    @Max(value = 4, message = "Номер уравнения должен быть не больше 4.")
    private int number;

    @NotBlank(message = "Название метода не должно быть пустым.")
    private String method;
    private double y0;
    private double x0;
    private double xn;

    @Positive(message = "Шаг h должен быть положительным.")
    private double h;

    @Positive(message = "Погрешность epsilon должна быть положительной.")
    private double epsilon;

    @AssertTrue(message = "Есть решения только для методов Эйлера, Рунге-Кутта 4 порядка и Милна.")
    public boolean isMethodAllowed() {
        return "euler".equals(method) || "runge".equals(method) || "miln".equals(method);
    }

    @AssertTrue(message = "Левая граница должна быть меньше чем правая.")
    public boolean areBordersNormal() {
        return x0 < xn;
    }

    @AssertTrue(message = "Для уравнения y' = 2x(1 + y^2) выбранный интервал пересекает точку разрыва решения.")
    public boolean isSecondEquationIntervalAllowed() {
        if (number != 2) {
            return true;
        }

        double minSquare = x0 <= 0 && xn >= 0 ? 0 : Math.min(x0 * x0, xn * xn);
        double maxSquare = Math.max(x0 * x0, xn * xn);

        double shift = Math.atan(y0) - x0 * x0;

        double minArgument = minSquare + shift - TAN_ASYMPTOTE;
        double maxArgument = maxSquare + shift + TAN_ASYMPTOTE;

        int firstAsymptoteIndex = (int) Math.ceil((minArgument - Math.PI / 2) / Math.PI);
        int lastAsymptoteIndex = (int) Math.floor((maxArgument - Math.PI / 2) / Math.PI);

        return firstAsymptoteIndex > lastAsymptoteIndex;
    }
}
