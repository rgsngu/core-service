package com.trading.platform.core.service;

import com.trading.platform.core.client.NseClient;
import com.trading.platform.core.entity.OptionChain;
import com.trading.platform.core.repository.OptionChainRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptionChainIngestionService {

    private static final Logger log =
            LoggerFactory.getLogger(OptionChainIngestionService.class);

    private final NseClient client;
    private final OptionChainParser parser;
    private final OptionChainRepository repository;

    public void ingest(String symbol) {
        String json = "";//client.getOptionChainJson(symbol);

        if (json == null) {
            log.error("Failed to fetch data for {}", symbol);
            return;
        }

        OptionChain chain = parser.parse(symbol, json);

        if (chain != null) {
            chain.getStrikes().forEach(s -> s.setOptionChain(chain));
            repository.save(chain);
            log.info("Saved option chain for {}", symbol);
        }
    }
}
