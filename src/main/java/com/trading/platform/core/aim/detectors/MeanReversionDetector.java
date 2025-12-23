package com.trading.platform.core.aim.detectors;

import com.trading.platform.core.aim.model.*;
import com.trading.platform.core.aim.signals.TradeSignal;

public class MeanReversionDetector implements Detector {

    @Override
    public String name() {
        return "MeanReversionDetector";
    }

    @Override
    public int priority() {
        return 1; // lower than momentum
    }

    @Override
    public TradeSignal detect(MarketSnapshot market,
                              IndicatorSnapshot indicators,
                              DecisionContext context) {

        if (indicators.getRsi() > 70) {
            context.setReason("Overbought → mean reversion SELL");
            return TradeSignal.SELL;
        }

        if (indicators.getRsi() < 30) {
            context.setReason("Oversold → mean reversion BUY");
            return TradeSignal.BUY;
        }

        return TradeSignal.HOLD;
    }
}
