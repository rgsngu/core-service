package com.trading.platform.core.ingestion.impl;

import com.trading.platform.core.aim.model.MarketSnapshot;
import com.trading.platform.core.ingestion.IngestionService;
import com.trading.platform.core.ingestion.client.ZerodhaClient;
import com.trading.platform.core.ingestion.client.dto.QuoteResponse;
import com.trading.platform.core.ingestion.mapper.TickToMarketSnapshotMapper;
import com.trading.platform.core.ingestion.parser.TickParser;
import com.trading.platform.core.kafka.producer.MarketSnapshotProducer;
import com.trading.platform.core.model.Tick;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Profile("live")
@RequiredArgsConstructor
public class ZerodhaIngestionService implements IngestionService {

    private final ZerodhaClient zerodhaClient;
    private final TickParser tickParser;
    private final TickToMarketSnapshotMapper mapper;
    private final MarketSnapshotProducer producer;


    @Override
    public MarketSnapshot getLatestSnapshot() {
        throw new UnsupportedOperationException(
                "Use batch ingestion for live polling"
        );
    }

    public List<Tick> pollBatch() {

        String instruments = System.getenv("INSTRUMENTS");
        if (instruments == null || instruments.isBlank()) {
            instruments = "NSE:RELIANCE,NSE:TCS";
        }

        List<String> instrumentList =
                Arrays.stream(instruments.split(","))
                        .toList();

        QuoteResponse response =
                zerodhaClient.getQuoteForBatch(instrumentList);

        return tickParser.parseBatch(response);
    }

    public void handleTick(Tick tick) {
        MarketSnapshot snapshot = mapper.map(tick);
        producer.publish(snapshot);
    }
}

