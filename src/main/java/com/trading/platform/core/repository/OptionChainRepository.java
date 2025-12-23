package com.trading.platform.core.repository;

import com.trading.platform.core.entity.OptionChain;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Profile("!local")
@Repository
public interface OptionChainRepository extends JpaRepository<OptionChain, Long> {

    Optional<OptionChain> findTopBySymbolOrderByTimestampDesc(String symbol);
}
