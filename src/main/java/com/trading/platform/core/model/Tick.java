package com.trading.platform.core.model;

import lombok.Data;

@Data
public class Tick {
    private String symbol;
    private double price;
    private long timestamp;
    private Double volume;
    private Long oi;

    public Tick(String symbol, double price, long timestamp, Double volume,Long oi) {
        this.symbol = symbol;
        this.price = price;
        this.timestamp = timestamp;
        this.volume = volume;
        this.oi = oi;
    }
}
