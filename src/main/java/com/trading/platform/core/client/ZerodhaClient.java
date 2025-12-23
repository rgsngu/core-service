package com.trading.platform.core.ingestion.client;

import com.trading.platform.core.aim.model.MarketSnapshot;
import com.trading.platform.core.ingestion.client.dto.QuoteResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class ZerodhaClient {

    private final RestTemplate restTemplate;
    private final String kiteBase = "https://api.kite.trade";

    // read from env — safer than hardcoding. You can wire these with @Value if you prefer.
    private final String apiKey = System.getenv("ZERODHA_API_KEY");
    private final String accessToken = System.getenv("ZERODHA_ACCESS_TOKEN");

    public ZerodhaClient() {
        // simple RestTemplate; timeouts can be tuned
        this.restTemplate = new RestTemplate();
        // Optional: configure request factory for timeouts if needed
    }

    @Retry(name = "zerodhaRetry")
    @CircuitBreaker(name = "zerodhaCB", fallbackMethod = "fallback")
    public MarketSnapshot getLatestMarketSnapshot() {

        QuoteResponse response =
                getQuoteForBatch(Arrays.asList("NSE:NIFTY"));

        return mapToMarketSnapshot(response);
    }

    @Retry(name = "zerodhaRetry")
    @CircuitBreaker(name = "zerodhaCB", fallbackMethod = "fallbackQuote")
    public QuoteResponse getQuoteForBatch(List<String> instrumentParam) {
        if (apiKey == null || accessToken == null) {
            throw new IllegalStateException("Zerodha API key/access token not set. " +
                    "Set ZERODHA_API_KEY and ZERODHA_ACCESS_TOKEN env variables.");
        }

        String url = UriComponentsBuilder.fromUriString(kiteBase + "/quote")
                .queryParam("i", instrumentParam)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Kite-Version", "3");
        headers.set(HttpHeaders.AUTHORIZATION, "token " + apiKey + ":" + accessToken);

        HttpEntity<Void> req = new HttpEntity<>(headers);

        ResponseEntity<QuoteResponse> res = restTemplate.exchange(url, HttpMethod.GET, req, QuoteResponse.class);

        if (res.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Zerodha quote request failed: " + res.getStatusCode());
        }
        return res.getBody();
    }

    /**
     * Convenience single-instrument LTP call.
     */
    public String getLtp(String instrument) {
        String url = UriComponentsBuilder.fromUriString(kiteBase + "/quote/ltp")
                .queryParam("i", instrument)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Kite-Version", "3");
        headers.set(HttpHeaders.AUTHORIZATION, "token " + apiKey + ":" + accessToken);

        HttpEntity<Void> req = new HttpEntity<>(headers);
        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, req, String.class);

        if (res.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Zerodha ltp request failed: " + res.getStatusCode());
        }
        return res.getBody();
    }

    public MarketSnapshot fallbackSnapshot(Exception ex) {

        System.err.println(
                "[ZerodhaFallback] API unavailable: " + ex.getMessage()
        );

        MarketSnapshot snapshot = new MarketSnapshot();
        snapshot.setSymbol("NIFTY");
        snapshot.setLtp(0); // or last known price
        snapshot.setCallOi(0);
        snapshot.setPutOi(0);
        snapshot.setTimestamp(System.currentTimeMillis());

        return snapshot;
    }

    public QuoteResponse fallbackQuote(
            List<String> instruments,
            Exception ex) {

        System.err.println(
                "[ZerodhaClient] Fallback triggered: " + ex.getMessage()
        );

        // Return EMPTY / SAFE response
        return QuoteResponse.empty();
    }

    private String buildQuery(List<String> instruments) {
        StringBuilder sb = new StringBuilder("?");
        instruments.forEach(i -> sb.append("i=").append(i).append("&"));
        return sb.toString();
    }

    private MarketSnapshot mapToMarketSnapshot(QuoteResponse response) {

        if (response == null || response.getData().isEmpty()) {
            return fallbackSnapshot(
                    new RuntimeException("Empty quote response")
            );
        }

        // Using NIFTY as example
        QuoteResponse.Quote quote =
                response.getData().get("NSE:NIFTY");

        MarketSnapshot snapshot = new MarketSnapshot();
        snapshot.setSymbol("NIFTY");
        snapshot.setLtp(quote.getLastPrice());
        snapshot.setCallOi(quote.getOi());
        snapshot.setPutOi(quote.getOi()); // placeholder
        snapshot.setTimestamp(System.currentTimeMillis());

        return snapshot;
    }

}
