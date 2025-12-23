package com.trading.platform.core.scheduler;

import com.trading.platform.core.aim.engine.AIMEngine;
import com.trading.platform.core.aim.model.DecisionContext;
import com.trading.platform.core.aim.model.MarketSnapshot;
import com.trading.platform.core.ingestion.IngestionService;
import com.trading.platform.core.order.service.PaperTradingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AIMScheduler {

    private final AIMEngine aimEngine;
    private final IngestionService ingestionService;
    private final PaperTradingService paperTradingService;

    public AIMScheduler(AIMEngine aimEngine,
                        IngestionService ingestionService,
                        PaperTradingService paperTradingService) {
        this.aimEngine = aimEngine;
        this.ingestionService = ingestionService;
        this.paperTradingService = paperTradingService;
    }

    @Scheduled(fixedRate = 5000)
    public void run() {

        MarketSnapshot market =
                ingestionService.getLatestSnapshot();

        DecisionContext decision =
                aimEngine.evaluate(market);

        paperTradingService.handleDecision(decision, market);
    }
}
