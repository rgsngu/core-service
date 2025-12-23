package com.trading.platform.core.aim.engine;

import com.trading.platform.core.aim.detectors.Detector;
import com.trading.platform.core.aim.detectors.MeanReversionDetector;
import com.trading.platform.core.aim.detectors.OptionMomentumDetector;
import com.trading.platform.core.aim.model.*;
import com.trading.platform.core.aim.signals.TradeSignal;
import com.trading.platform.core.service.IndicatorService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AIMEngine {

    private final List<Detector> detectors;
    private final IndicatorService indicatorService;

    public AIMEngine(IndicatorService indicatorService) {
        this.indicatorService = indicatorService;
        this.detectors = List.of(
                new OptionMomentumDetector(),
                new MeanReversionDetector()
        );
    }

    public DecisionContext evaluate(MarketSnapshot market) {

        IndicatorSnapshot indicators =
                indicatorService.calculate(market);

        DecisionContext bestDecision = new DecisionContext();
        bestDecision.setSignal(TradeSignal.HOLD);
        int bestPriority = -1;

        for (Detector detector : detectors) {

            DecisionContext tempContext = new DecisionContext();
            TradeSignal signal =
                    detector.detect(market, indicators, tempContext);

            if (signal != TradeSignal.HOLD &&
                    detector.priority() > bestPriority) {

                bestPriority = detector.priority();
                bestDecision.setSignal(signal);
                bestDecision.setDetectorName(detector.name());
                bestDecision.setReason(tempContext.getReason());
            }
        }


        return bestDecision;
    }
}
