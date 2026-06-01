package com.github.vodobryshkin.cauchyproblem.domain.method;

import com.github.vodobryshkin.cauchyproblem.dto.Table;

public interface Method {
    Table table(double y0, double x0, double xn, double h);
}
