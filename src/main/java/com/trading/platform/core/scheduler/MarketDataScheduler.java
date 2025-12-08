package com.trading.platform.core.scheduler;

import com.trading.platform.core.client.ZerodhaClient;
import com.trading.platform.core.ingestion.IngestionService;
import com.trading.platform.core.model.Tick;
import com.trading.platform.core.service.OptionChainIngestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MarketDataScheduler {

    private final ZerodhaClient zerodhaClient;
    private final IngestionService ingestionService;

    @Scheduled(fixedRateString = "${ingestion.poll-rate-ms:1000}")
    public void fetch() {
        try {
            String json = zerodhaClient.getQuoteForBatch(List.of("NSE:RELIANCE","NSE:TCS"));
            List<Tick> ticks = tickParser.parseBatch(json);
            ticks.forEach(ingestionService::handleTick);
        } catch(Exception e) {
            log.error("market fetch failed", e);
        }
    }
}
