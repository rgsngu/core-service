package com.trading.platform.core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sample")
@RequiredArgsConstructor
public class SampleController {


    @GetMapping("/health")
    public String health() {
        return "Core Service UP";
    }
}
