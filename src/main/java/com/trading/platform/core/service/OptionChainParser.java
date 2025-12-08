package com.trading.platform.core.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.platform.core.entity.*;
import com.trading.platform.core.entity.enums.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
@Slf4j
public class OptionChainParser {

    private final ObjectMapper mapper = new ObjectMapper();

    public OptionChain parse(String symbol, String json) {
        try {

            JsonNode root = mapper.readTree(json);
            JsonNode data = root.path("records").path("data");

            List<OptionStrike> strikes = new ArrayList<>();

            for (JsonNode item : data) {

                double strikePrice = item.path("strikePrice").asDouble();

                // CE
                if (item.has("CE")) {
                    JsonNode ce = item.get("CE");
                    strikes.add(buildStrike(symbol, ce, strikePrice, OptionType.CE));
                }

                // PE
                if (item.has("PE")) {
                    JsonNode pe = item.get("PE");
                    strikes.add(buildStrike(symbol, pe, strikePrice, OptionType.PE));
                }
            }

            return OptionChain.builder()
                    .symbol(symbol)
                    .expiry(root.path("records").path("expiryDates").get(0).asText())
                    .expiryType(ExpiryType.WEEKLY)
                    .timestamp(LocalDateTime.now())
                    .strikes(strikes)
                    .build();

        } catch (Exception ex) {
            log.error("Error parsing NSE Option Chain: {}", ex.getMessage());
            return null;
        }
    }

    private OptionStrike buildStrike(String symbol, JsonNode node,
                                     double strike, OptionType type) {

        return OptionStrike.builder()
                .symbol(symbol)
                .strikePrice(strike)
                .type(type)
                .lastTradedPrice(node.path("lastPrice").asDouble())
                .openInterest(node.path("openInterest").asDouble())
                .changeInOI(node.path("changeinOpenInterest").asDouble())
                .iv(node.path("impliedVolatility").asDouble())
                .delta(0.0)
                .theta(0.0)
                .gamma(0.0)
                .vega(0.0)
                .build();
    }
}
