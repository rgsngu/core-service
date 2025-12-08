package com.trading.platform.core.dto;

import com.trading.platform.core.entity.enums.OptionType;
import lombok.*;

@Getter @Setter @Builder
public class OptionStrikeDto {
    private Double strikePrice;
    private OptionType type;
    private Double lastTradedPrice;
    private Double openInterest;
    private Double iv;
}
