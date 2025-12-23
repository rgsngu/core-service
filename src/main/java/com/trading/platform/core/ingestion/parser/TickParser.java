package com.trading.platform.core.ingestion.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.platform.core.ingestion.client.dto.QuoteResponse;
import com.trading.platform.core.model.Tick;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TickParser {
    private final ObjectMapper om = new ObjectMapper();
    private final Logger log = LoggerFactory.getLogger(TickParser.class);

    /**
     * Parse a Zerodha /quote batch response and return a list of ticks.
     * This method makes conservative assumptions:
     * - top-level JSON contains "data": { "NSE:RELIANCE": { ... }, "NSE:TCS": { ... } }
     * - price field could be "last_price" or inside "ohlc" -> "close"
     *
     * Extend this method if your Zerodha JSON has different shape.
     */
    public List<Tick> parseBatch(QuoteResponse response) {
        List<Tick> out = new ArrayList<>();
        if (response == null || response.getData().isEmpty()) return out;

        try {
            for (Map.Entry<String, QuoteResponse.Quote> entry
                    : response.getData().entrySet()) {

                String instrument = entry.getKey(); // e.g. NSE:RELIANCE
                QuoteResponse.Quote quote = entry.getValue();

                double ltp = quote.getLastPrice();
                if (ltp <= 0) {
                    log.debug("Invalid LTP for {}", instrument);
                    continue;
                }

                String symbol =
                        instrument.contains(":")
                                ? instrument.split(":", 2)[1]
                                : instrument;

                Tick tick = new Tick(
                        symbol,
                        ltp,
                        System.currentTimeMillis(),
                        null
                );

                out.add(tick);
            }
        } catch (Exception e) {
            log.error("Failed to parse Zerodha QuoteResponse", e);
        }
        return out;
    }
}
