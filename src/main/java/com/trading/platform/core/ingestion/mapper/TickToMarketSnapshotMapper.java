package com.trading.platform.core.ingestion.mapper;

import com.trading.platform.core.aim.model.MarketSnapshot;
import com.trading.platform.core.model.Tick;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TickToMarketSnapshotMapper {

    public MarketSnapshot map(Tick tick) {

        MarketSnapshot snapshot = new MarketSnapshot();
        snapshot.setEventId(UUID.randomUUID().toString());
        snapshot.setSymbol(tick.getSymbol());
        snapshot.setLtp(tick.getPrice());
        snapshot.setTimestamp(tick.getTimestamp());

        if (tick.getOi() != null) {
            snapshot.setCallOi(tick.getOi());
            snapshot.setPutOi(tick.getOi()); // placeholder (split later)
        }

        return snapshot;
    }

}
