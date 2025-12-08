package com.trading.platform.core.service.impl;

import com.trading.platform.core.dto.*;
import com.trading.platform.core.entity.OptionChain;
import com.trading.platform.core.entity.OptionStrike;
import com.trading.platform.core.repository.OptionChainRepository;
import com.trading.platform.core.service.OptionChainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OptionChainServiceImpl implements OptionChainService {

    private final OptionChainRepository repository;

    @Override
    public OptionChainResponse getLatestChain(String symbol) {
        OptionChain chain = repository.findTopBySymbolOrderByTimestampDesc(symbol)
                .orElseThrow(() -> new RuntimeException("No data for " + symbol));

        return OptionChainResponse.builder()
                .symbol(chain.getSymbol())
                .expiry(chain.getExpiry())
                .strikes(
                        chain.getStrikes()
                                .stream()
                                .map(s -> OptionStrikeDto.builder()
                                        .strikePrice(s.getStrikePrice())
                                        .type(s.getType())
                                        .lastTradedPrice(s.getLastTradedPrice())
                                        .openInterest(s.getOpenInterest())
                                        .iv(s.getIv())
                                        .build()
                                ).collect(Collectors.toList())
                )
                .build();
    }
}
