package com.trading.platform.core.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class NseClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public String getOptionChainJson(String symbol) {
        try {
            String url = "https://www.nseindia.com/api/option-chain-indices?symbol=" + symbol;

            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0");
            headers.set("Accept", "application/json");
            headers.set("Accept-Language", "en-US,en;q=0.9");
            headers.set("Referer", "https://www.nseindia.com/option-chain");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            return response.getBody();

        } catch (Exception ex) {
            log.error("Error fetching NSE option chain: {}", ex.getMessage());
            return null;
        }
    }
}
