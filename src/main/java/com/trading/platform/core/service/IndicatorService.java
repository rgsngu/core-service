package com.trading.platform.core.service;

import com.trading.platform.core.aim.model.IndicatorSnapshot;
import com.trading.platform.core.aim.model.MarketSnapshot;

public interface IndicatorService {

    IndicatorSnapshot calculate(MarketSnapshot market);
}

