package com.trading.platform.core.service;

import com.trading.platform.core.dto.OptionChainResponse;

public interface OptionChainService {
    OptionChainResponse getLatestChain(String symbol);
}
