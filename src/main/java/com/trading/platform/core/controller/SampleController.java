package com.trading.platform.core.controller;

import com.trading.platform.core.service.SampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sample")
@RequiredArgsConstructor
public class SampleController {

    private final SampleService service;

    @PostMapping
    public String test(@RequestParam String msg) {
        return service.createSample(msg);
    }

    @GetMapping("/health")
    public String health() {
        return "Core Service UP";
    }
}
