package com.trading.platform.core.aim.model;

public class MarketSnapshot {

    private String symbol;
    private double ltp;
    private long timestamp;
    private String eventId;
    private double callOi;
    private double putOi;



    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getLtp() {
        return ltp;
    }

    public void setLtp(double ltp) {
        this.ltp = ltp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getCallOi() {
        return callOi;
    }

    public void setCallOi(double callOi) {
        this.callOi = callOi;
    }

    public double getPutOi() {
        return putOi;
    }

    public void setPutOi(double putOi) {
        this.putOi = putOi;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
