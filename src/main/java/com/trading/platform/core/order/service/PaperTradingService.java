package com.trading.platform.core.order.service;

import com.trading.platform.core.aim.model.DecisionContext;
import com.trading.platform.core.aim.signals.TradeSignal;
import com.trading.platform.core.aim.model.MarketSnapshot;
import com.trading.platform.core.order.model.*;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PaperTradingService {

    private final Map<String, Position> positions = new HashMap<>();

    private final Set<String> processedEvents =
            ConcurrentHashMap.newKeySet();

    public void handleDecision(DecisionContext decision,
                               MarketSnapshot market) {

        if (processedEvents.contains(market.getEventId())) {
            return; // already processed
        }

        if (decision.getSignal() == TradeSignal.HOLD) {
            return;
        }

        processedEvents.add(market.getEventId());

        OrderSide side =
                decision.getSignal() == TradeSignal.BUY
                        ? OrderSide.BUY
                        : OrderSide.SELL;

        executeOrder(market.getSymbol(), side, market.getLtp(), 1);
    }

    private void executeOrder(String symbol,
                              OrderSide side,
                              double price,
                              int qty) {

        Position position =
                positions.getOrDefault(symbol, new Position());

        position.setSymbol(symbol);

        if (side == OrderSide.BUY) {
            double totalCost =
                    position.getAvgPrice() * position.getQuantity()
                            + price * qty;

            position.setQuantity(position.getQuantity() + qty);
            position.setAvgPrice(
                    totalCost / position.getQuantity()
            );
        } else {
            position.setQuantity(position.getQuantity() - qty);
        }

        position.setUnrealizedPnl(
                (price - position.getAvgPrice())
                        * position.getQuantity()
        );

        positions.put(symbol, position);

        System.out.println(
                "[PAPER-TRADE] " + side +
                        " " + symbol +
                        " @ " + price +
                        " | QTY=" + position.getQuantity() +
                        " | PNL=" + position.getUnrealizedPnl()
        );
    }

    public void printSummary() {
        System.out.println("========== BACKTEST SUMMARY ==========");
        positions.values().forEach(p ->
                System.out.println(
                        p.getSymbol() +
                                " QTY=" + p.getQuantity() +
                                " AVG=" + p.getAvgPrice() +
                                " PNL=" + p.getUnrealizedPnl()
                )
        );
    }

}
