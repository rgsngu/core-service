package com.trading.platform.core.ingestion.kafka;

import com.trading.platform.core.model.Tick;
import org.springframework.stereotype.Component;

@Component
public class TickProducer {
    public void publish(Tick tick) {
        // TODO: Kafka template send
    }
}
