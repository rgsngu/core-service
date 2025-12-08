package com.trading.platform.core.model;

import lombok.Data;

@Data
public class Candle {
    private String symbol;
    private double ltp;
    private long timestamp; // epoch millis
    private Double volume;  // optional
}
