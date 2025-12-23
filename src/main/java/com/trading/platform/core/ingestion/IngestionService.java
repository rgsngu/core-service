package com.trading.platform.core.ingestion;

import com.trading.platform.core.aim.model.MarketSnapshot;

public interface IngestionService {
    MarketSnapshot getLatestSnapshot();
}
