package com.trading.platform.core.controller;

import com.trading.platform.core.aim.engine.AIMEngine;
import com.trading.platform.core.aim.model.DecisionContext;
import com.trading.platform.core.aim.model.MarketSnapshot;
import com.trading.platform.core.dto.AimDecisionRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/aim")
public class AIMController {

    private final AIMEngine aimEngine;

    public AIMController(AIMEngine aimEngine) {
        this.aimEngine = aimEngine;
    }

    @PostMapping("/evaluate")
    public DecisionContext evaluate(@RequestBody AimDecisionRequest request) {

        MarketSnapshot market = new MarketSnapshot();
        market.setSymbol(request.getSymbol());
        market.setLtp(request.getLtp());
        market.setCallOi(request.getCallOi());
        market.setPutOi(request.getPutOi());
        market.setTimestamp(System.currentTimeMillis());

        return aimEngine.evaluate(market);
    }
}
