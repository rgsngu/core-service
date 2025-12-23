package com.trading.platform.core.backtest;

import com.trading.platform.core.aim.engine.AIMEngine;
import com.trading.platform.core.aim.model.DecisionContext;
import com.trading.platform.core.aim.model.MarketSnapshot;
import com.trading.platform.core.ingestion.backtest.BacktestDataProvider;
import com.trading.platform.core.order.service.PaperTradingService;
import org.springframework.stereotype.Component;

@Component
public class BacktestRunner {

    private final AIMEngine aimEngine;
    private final PaperTradingService paperTradingService;
    private final BacktestDataProvider dataProvider;

    public BacktestRunner(AIMEngine aimEngine,
                          PaperTradingService paperTradingService,
                          BacktestDataProvider dataProvider) {
        this.aimEngine = aimEngine;
        this.paperTradingService = paperTradingService;
        this.dataProvider = dataProvider;
    }

    public void run() {

        while (dataProvider.hasNext()) {

            MarketSnapshot market = dataProvider.next();

            DecisionContext decision =
                    aimEngine.evaluate(market);

            paperTradingService.handleDecision(decision, market);
        }

        paperTradingService.printSummary();
    }
}
