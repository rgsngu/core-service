package com.trading.platform.core.repository;

import com.trading.platform.core.entity.Sample;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<Sample, Long> { }
