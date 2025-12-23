package com.trading.platform.core.controller;

import com.trading.platform.core.aim.model.MarketSnapshot;
import com.trading.platform.core.backtest.BacktestRunner;
import com.trading.platform.core.ingestion.backtest.BacktestDataProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/backtest")
public class BacktestController {

    private final BacktestRunner runner;
    private final BacktestDataProvider provider;

    public BacktestController(BacktestRunner runner,
                              BacktestDataProvider provider) {
        this.runner = runner;
        this.provider = provider;
    }

    private MarketSnapshot snapshot(String symbol, double ltp) {
        MarketSnapshot m = new MarketSnapshot();
        m.setSymbol(symbol);
        m.setLtp(ltp);
        m.setCallOi(150000);
        m.setPutOi(120000);
        m.setTimestamp(System.currentTimeMillis());
        return m;
    }

    @PostMapping("/run")
    public String runBacktest() {

        provider.load( List.of(
                snapshot("NIFTY", 22100),
                snapshot("NIFTY", 22110),
                snapshot("NIFTY", 22125),
                snapshot("NIFTY", 22140),
                snapshot("NIFTY", 22120),
                snapshot("NIFTY", 22105)
        ));
        runner.run();

        return "Backtest completed";
    }
}
