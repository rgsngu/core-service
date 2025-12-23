package com.trading.platform.core.ingestion.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteResponse {

    private String status;

    private Map<String, Quote> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, Quote> getData() {
        return data == null ? Collections.emptyMap() : data;
    }

    public void setData(Map<String, Quote> data) {
        this.data = data;
    }

    // =============================
    // INNER QUOTE OBJECT
    // =============================

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Quote {

        @JsonProperty("last_price")
        private double lastPrice;

        @JsonProperty("volume")
        private long volume;

        @JsonProperty("oi")
        private long oi;

        public double getLastPrice() { return lastPrice; }
        public long getVolume() { return volume; }
        public long getOi() { return oi; }
    }

    // =============================
    // SAFE EMPTY RESPONSE
    // =============================

    public static QuoteResponse empty() {
        QuoteResponse r = new QuoteResponse();
        r.setStatus("error");
        r.setData(Collections.emptyMap());
        return r;
    }
}
