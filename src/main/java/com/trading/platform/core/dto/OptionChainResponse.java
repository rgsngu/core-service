package com.trading.platform.core.dto;

import lombok.*;
import java.util.List;

@Getter @Setter @Builder
public class OptionChainResponse {
    private String symbol;
    private String expiry;
    private List<OptionStrikeDto> strikes;
}
