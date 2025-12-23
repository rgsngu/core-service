package com.trading.platform.core.service.history;

import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;

@Component
public class PriceHistoryStore {

    private static final int WINDOW_SIZE = 14;
    private final Deque<Double> prices = new ArrayDeque<>();

    public void addPrice(double price) {
        if (prices.size() == WINDOW_SIZE) {
            prices.pollFirst();
        }
        prices.addLast(price);
    }

    public Deque<Double> getPrices() {
        return prices;
    }

    public boolean isReady() {
        return prices.size() == WINDOW_SIZE;
    }
}
