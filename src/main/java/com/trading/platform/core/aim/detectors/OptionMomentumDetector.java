package com.trading.platform.core.aim.detectors;

import com.trading.platform.core.aim.model.*;
import com.trading.platform.core.aim.signals.TradeSignal;

public class OptionMomentumDetector implements Detector {

    @Override
    public String name() {
        return "OptionMomentumDetector";
    }

    @Override
    public int priority() {
        return 2;
    }

    public TradeSignal detect(MarketSnapshot market,
                              IndicatorSnapshot indicators,
                              DecisionContext context) {

        if (indicators.getRsi() > 60 && indicators.getOiDelta() > 0) {
            context.setReason("RSI > 60 and Call OI rising");
            return TradeSignal.BUY;
        }

        if (indicators.getRsi() < 40 && indicators.getOiDelta() < 0) {
            context.setReason("RSI < 40 and Put OI rising");
            return TradeSignal.SELL;
        }

        context.setReason("No momentum detected");
        return TradeSignal.HOLD;
    }
}
