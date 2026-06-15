package com.github.vodobryshkin.cauchyproblem.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ODERequest {
    @Min(value = 1, message = "Номер неравенства должен быть не меньше 1.")
    @Max(value = 4, message = "Номер неравенства должен быть не больше 4.")
    private int number;

    private String method;
    private double y0;
    private double x0;
    private double xn;

    @Min(value = 0, message = "Шаг должен быть меньше 0.")
    private double h;

    @Min(value = 0, message = "Погрешность должна быть больше 0.")
    private double epsilon;

    @AssertTrue(message = "Есть решения только для методов Эйлера, Рунге-Кутта 4 порядка и Милна.")
    public boolean isMethodAllowed() {
        return "euler".equals(method) || "runge".equals(method) || "miln".equals(method);
    }

    @AssertTrue(message = "Левая граница должна быть мменьше чем правая.")
    public boolean areBordersNormal() {
        return x0 < xn;
    }
}
