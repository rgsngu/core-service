package com.trading.platform.core.dto;

public class AimDecisionRequest {

    private String symbol;
    private double ltp;
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

    // getters & setters
}
