package com.trading.platform.core.service.impl;

import com.trading.platform.core.entity.Sample;
import com.trading.platform.core.repository.SampleRepository;
import com.trading.platform.core.service.SampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SampleServiceImpl implements SampleService {

    private final SampleRepository repository;

    @Override
    public String createSample(String msg) {
        Sample sample = Sample.builder().message(msg).build();
        repository.save(sample);
        return "Saved: " + msg;
    }
}
