package com.trading.platform.core.kafka.consumer;

import com.trading.platform.core.aim.engine.AIMEngine;
import com.trading.platform.core.aim.model.DecisionContext;
import com.trading.platform.core.aim.model.MarketSnapshot;
import com.trading.platform.core.order.service.PaperTradingService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MarketSnapshotConsumer {

    private final AIMEngine aimEngine;
    private final PaperTradingService paperTradingService;

    @KafkaListener(
            topics = "market.snapshot.v1",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void onMessage(MarketSnapshot snapshot, Acknowledgment ack) {

        DecisionContext decision = aimEngine.evaluate(snapshot);
        paperTradingService.handleDecision(decision, snapshot);
        ack.acknowledge();
    }
}
