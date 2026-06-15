package com.github.vodobryshkin.cauchyproblem.service.spec;

import com.github.vodobryshkin.cauchyproblem.dto.ODERequest;
import com.github.vodobryshkin.cauchyproblem.dto.Solution;

public interface IODESolutionService {
    Solution result(ODERequest request);
}
