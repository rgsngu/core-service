package com.trading.platform.core.aim.detectors;

import com.trading.platform.core.aim.model.DecisionContext;
import com.trading.platform.core.aim.model.IndicatorSnapshot;
import com.trading.platform.core.aim.model.MarketSnapshot;
import com.trading.platform.core.aim.signals.TradeSignal;

public interface Detector {
    String name();

    int priority();
    // Higher number = higher priority

    TradeSignal detect(MarketSnapshot market,
                       IndicatorSnapshot indicators,
                       DecisionContext context);
}
