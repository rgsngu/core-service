package com.trading.platform.core.controller;

import com.trading.platform.core.dto.OptionChainResponse;
import com.trading.platform.core.service.OptionChainIngestionService;
import com.trading.platform.core.service.OptionChainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/option-chain")
@RequiredArgsConstructor
public class OptionChainController {

    private final OptionChainService service;
    private final OptionChainIngestionService ingestionService;

    @GetMapping("/{symbol}")
    public OptionChainResponse getLatest(@PathVariable String symbol) {
        return service.getLatestChain(symbol.toUpperCase());
    }

    @GetMapping("/fetch/{symbol}")
    public String fetch(@PathVariable String symbol) {
        ingestionService.ingest(symbol);
        return "Triggered fetch for " + symbol;
    }

}
