package com.trading.platform.core.kafka.producer;

import com.trading.platform.core.aim.model.MarketSnapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketSnapshotProducer {

    private final KafkaTemplate<String, MarketSnapshot> kafkaTemplate;

    private static final String TOPIC = "market.snapshot.v1";

    public void publish(MarketSnapshot snapshot) {
        kafkaTemplate.send(TOPIC, snapshot.getSymbol(), snapshot);
    }
}
