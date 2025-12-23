package com.trading.platform.core.aim.model;

import com.trading.platform.core.aim.signals.TradeSignal;

public class DecisionContext {

    private String detectorName;
    private String reason;
    private TradeSignal signal;


    public String getDetectorName() {
        return detectorName;
    }

    public void setDetectorName(String detectorName) {
        this.detectorName = detectorName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public TradeSignal getSignal() {
        return signal;
    }

    public void setSignal(TradeSignal signal) {
        this.signal = signal;
    }
}
