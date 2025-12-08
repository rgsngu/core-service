package com.trading.platform.core.entity;

import com.trading.platform.core.entity.enums.OptionType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "option_strike")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class OptionStrike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol; // NIFTY, BANKNIFTY

    private Double strikePrice;

    @Enumerated(EnumType.STRING)
    private OptionType type; // CE or PE

    private Double lastTradedPrice;
    private Double openInterest;
    private Double changeInOI;

    private Double iv;    // implied volatility
    private Double delta; // greeks
    private Double theta;
    private Double gamma;
    private Double vega;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chain_id")
    private OptionChain optionChain;
}
