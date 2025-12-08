package com.trading.platform.core.model;

import lombok.Data;

@Data
public class Tick {
    private String symbol;
    private double ltp;
    private long timestamp;
}
