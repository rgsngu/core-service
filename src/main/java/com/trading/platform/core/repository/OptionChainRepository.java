package com.trading.platform.core.repository;

import com.trading.platform.core.entity.OptionChain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OptionChainRepository extends JpaRepository<OptionChain, Long> {

    Optional<OptionChain> findTopBySymbolOrderByTimestampDesc(String symbol);
}
