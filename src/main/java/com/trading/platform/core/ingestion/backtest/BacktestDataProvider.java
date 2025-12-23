package com.trading.platform.core.ingestion.backtest;

import com.trading.platform.core.aim.model.MarketSnapshot;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
public class BacktestDataProvider {

    private Iterator<MarketSnapshot> iterator;

    public void load(List<MarketSnapshot> history) {
        this.iterator = history.iterator();
    }

    public boolean hasNext() {
        return iterator != null && iterator.hasNext();
    }

    public MarketSnapshot next() {
        return iterator.next();
    }
}
