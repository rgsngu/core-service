package com.trading.platform.core.ingestion.scheduler;

import com.trading.platform.core.ingestion.client.ZerodhaClient;
import com.trading.platform.core.ingestion.client.dto.QuoteResponse;
import com.trading.platform.core.ingestion.impl.ZerodhaIngestionService;
import com.trading.platform.core.model.Tick;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Scheduler that polls Zerodha REST API at configured interval.
 *
 * Configurable property: ingestion.poll-rate-ms (default 1000)
 */
@Component
@Profile("live")
@RequiredArgsConstructor
public class MarketDataScheduler {
    private final Logger log = LoggerFactory.getLogger(MarketDataScheduler.class);

    private final ZerodhaIngestionService ingestionService;
    private final ZerodhaClient zerodhaClient;

    // NOTE: uses fixedRateString so you can change in application.yml without recompiling
    @Scheduled(fixedRateString = "${ingestion.poll-rate-ms:1000}")
    @Profile("live")
    public void poll() {
        try {
            String instrumentsEnv = System.getenv("INSTRUMENTS");
            if (instrumentsEnv == null || instrumentsEnv.isBlank()) {
                instrumentsEnv = "NSE:RELIANCE,NSE:TCS";
            }
            List<String> instruments =
                    Arrays.stream(instrumentsEnv.split(","))
                            .toList();
            QuoteResponse response =
                    zerodhaClient.getQuoteForBatch(instruments);

            List<Tick> ticks = ingestionService.pollBatch();
            for (Tick t : ticks) {
                ingestionService.handleTick(t);
            }
        } catch (Exception e) {
            log.error("Market data poll failed: {}", e.getMessage(), e);
        }
    }


}
