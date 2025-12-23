package com.trading.platform.core.aim.model;

public class IndicatorSnapshot {

    private double rsi;
    private double vwap;
    private double oiDelta;

    public double getRsi() {
        return rsi;
    }

    public void setRsi(double rsi) {
        this.rsi = rsi;
    }

    public double getVwap() {
        return vwap;
    }

    public void setVwap(double vwap) {
        this.vwap = vwap;
    }

    public double getOiDelta() {
        return oiDelta;
    }

    public void setOiDelta(double oiDelta) {
        this.oiDelta = oiDelta;
    }
}
