package com.github.vodobryshkin.cauchyproblem.controller;

import com.github.vodobryshkin.cauchyproblem.dto.ODERequest;
import com.github.vodobryshkin.cauchyproblem.dto.Solution;
import com.github.vodobryshkin.cauchyproblem.service.spec.IODESolutionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ode")
public class ODEController {
    private final IODESolutionService solutionService;

    @PostMapping
    public ResponseEntity<Solution> handlePost(@Valid @RequestBody ODERequest request) {
        return ResponseEntity
                .ok()
                .body(solutionService.result(request));
    }
}
