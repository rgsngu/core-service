package com.trading.platform.core.repository;

import com.trading.platform.core.entity.OptionStrike;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Profile("!local")
@Repository
public interface OptionStrikeRepository extends JpaRepository<OptionStrike, Long> {}
