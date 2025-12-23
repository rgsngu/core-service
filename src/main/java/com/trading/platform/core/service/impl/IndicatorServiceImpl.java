package com.trading.platform.core.service.impl;

import com.trading.platform.core.aim.model.*;
import com.trading.platform.core.service.IndicatorService;
import com.trading.platform.core.service.history.PriceHistoryStore;
import org.springframework.stereotype.Service;

@Service
public class IndicatorServiceImpl implements IndicatorService {

    private final PriceHistoryStore priceHistoryStore;

    public IndicatorServiceImpl(PriceHistoryStore priceHistoryStore) {
        this.priceHistoryStore = priceHistoryStore;
    }

    @Override
    public IndicatorSnapshot calculate(MarketSnapshot market) {

        priceHistoryStore.addPrice(market.getLtp());

        IndicatorSnapshot indicators = new IndicatorSnapshot();
        indicators.setOiDelta(market.getCallOi() - market.getPutOi());

        if (priceHistoryStore.isReady()) {
            indicators.setRsi(calculateRSI());
        } else {
            indicators.setRsi(50.0); // neutral until ready
        }

        indicators.setVwap(market.getLtp()); // placeholder
        return indicators;
    }

    // ---------------- INDICATORS ----------------

    private double calculateRSI() {
        double gain = 0;
        double loss = 0;

        Double previous = null;

        for (Double price : priceHistoryStore.getPrices()) {
            if (previous != null) {
                double diff = price - previous;
                if (diff > 0) gain += diff;
                else loss -= diff;
            }
            previous = price;
        }

        if (loss == 0) return 100.0;

        double rs = gain / loss;
        return 100 - (100 / (1 + rs));
    }
}
