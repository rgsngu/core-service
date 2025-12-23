package com.trading.platform.core.ingestion.impl;

import com.trading.platform.core.aim.model.MarketSnapshot;
import com.trading.platform.core.ingestion.IngestionService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Profile({"docker", "dev", "backtest"})
public class MockIngestionService implements IngestionService {

    private final Random random = new Random();
    private double lastPrice = 22100;

    @Override
    public MarketSnapshot getLatestSnapshot() {

        lastPrice += random.nextDouble() * 10 - 5;

        MarketSnapshot snapshot = new MarketSnapshot();
        snapshot.setSymbol("NIFTY");
        snapshot.setLtp(lastPrice);
        snapshot.setCallOi(150000 + random.nextInt(2000));
        snapshot.setPutOi(120000 + random.nextInt(2000));
        snapshot.setTimestamp(System.currentTimeMillis());

        return snapshot;
    }
}
